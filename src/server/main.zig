const std = @import("std");
const mem = std.mem;
const net = std.net;

const Allocator = mem.Allocator;
const Thread = std.Thread;

const MessageType = enum {
    join_topic,
    update,
};

const JoinTopicMessage = struct { top_id: u8, player_nick: []const u8 };
const UpdateMessage = struct {
    top_id: u8,
    player_nick: []const u8,
    data: struct { score: u8, done_til: u8, max: u8 },
};

const Message = union(MessageType) {
    join_topic: JoinTopicMessage,
    update: UpdateMessage,
};

const Client = struct {
    _stream: std.net.Stream,
    addr: std.net.Address,
    nick: []const u8,
    score: u8,
    done_til: u8,
};

const Response = struct {
    variant: enum {
        ok,
        err,
    },
    msg: []const u8,
};

fn trimWhitespaces(data: []const u8) []const u8 {
    return std.mem.trim(u8, data, &[3]u8{ ' ', '\r', '\n' });
}

const Server = struct {
    allocator: std.mem.Allocator,
    address: std.net.Address,
    server: std.net.StreamServer,
    topics: std.AutoHashMap(u8, std.ArrayList(Client)),
    threads: std.ArrayList(Thread),

    fn init(alloc: Allocator) !Server {
        const address = std.net.Address.initIp4([4]u8{ 127, 0, 0, 1 }, 2257);
        const lobbies = std.AutoHashMap(u8, std.ArrayList(Client)).init(alloc);
        const threads = std.ArrayList(Thread).init(alloc);
        var server = std.net.StreamServer.init(.{ .reuse_address = true });

        try server.listen(address);
        return Server{
            .address = address,
            .server = server,
            .allocator = alloc,
            .topics = lobbies,
            .threads = threads,
        };
    }

    fn deinit(self: *Server) void {
        var topics_iterator = self.topics.valueIterator();

        // deinit values
        while (topics_iterator.next()) |*val| {
            val.*.*.deinit();
        }

        // kill threads
        for (self.threads.items) |*th| {
            th.*.join();
        }

        self.topics.deinit();
        self.server.deinit();
    }

    fn handleConnection(self: *Server, conn: *const net.StreamServer.Connection) !void {
        _ = self;
        // Echo server = placeholder
        while (true) {
            var buf: [1000]u8 = undefined;
            const dataSize = try conn.stream.read(buf[0..]);
            const data = buf[0..dataSize];

            _ = try conn.stream.write(data);
        }
    }

    fn acceptConnection(self: *Server, conn: net.StreamServer.Connection) !void {
        var buf: [1000]u8 = undefined;
        var data: []const u8 = undefined;
        var dataSize: usize = undefined;

        const msg = while (true) {
            dataSize = try conn.stream.read(buf[0..]);
            data = trimWhitespaces(buf[0..dataSize]);

            std.log.debug("got data from client ({s}), parsing", .{data});
            const msg = std.json.parseFromSlice(JoinTopicMessage, self.allocator, data, .{}) catch {
                std.log.debug("data recieved was invalid!", .{});
                const resp = Response{ .msg = "Invalid JSON data", .variant = .err };

                // write straight to the stream to avoid an allocation
                try std.json.stringify(resp, .{}, conn.stream.writer());
                continue;
            };
            break msg;
        };

        // deallocate heap slice
        defer msg.deinit();

        const lobbyId = msg.value.top_id;

        std.log.debug("registering the client to the topic", .{});
        var clientRepr = Client{
            ._stream = conn.stream,
            .addr = conn.address,
            .nick = msg.value.player_nick,
            .score = 0,
            .done_til = 0,
        };

        var entry = try self.topics.getOrPutValue(lobbyId, std.ArrayList(Client).init(self.allocator));
        var arr: *std.ArrayList(Client) = entry.value_ptr; // for the sake of it
        try arr.*.append(clientRepr);

        std.log.debug("registered client: {any}", .{clientRepr});

        const respMsg = try std.fmt.allocPrint(self.allocator, "successfully registered client to lobby {d}", .{lobbyId});
        const resp = Response{ .msg = respMsg, .variant = .ok };
        try std.json.stringify(resp, .{}, conn.stream.writer());

        try self.handleConnection(&conn);
        conn.stream.close();
    }

    fn threadFunc(self: *Server, conn: net.StreamServer.Connection) void {
        self.acceptConnection(conn) catch {
            @panic("thread panicked!");
        };
    }

    fn run(self: *Server) !void {
        while (true) {
            const conn = try self.server.accept();
            const thread = try Thread.spawn(.{}, Server.threadFunc, .{ self, conn });
            try self.threads.append(thread);
        }
    }
};

pub fn main() !void {
    var gpalloc = std.heap.GeneralPurposeAllocator(.{}){};
    var server = try Server.init(gpalloc.allocator());
    defer server.deinit();

    try server.run();
}
