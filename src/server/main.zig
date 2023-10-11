const std = @import("std");
const mem = std.mem;
const Allocator = mem.Allocator;

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
    stream: std.net.Stream,
    addr: std.net.Address,
    nick: []const u8,
    score: u8,
    done_til: u8,
};

fn trimWhitespaces(data: []const u8) []const u8 {
    return std.mem.trim(u8, data, &[3]u8{ ' ', '\r', '\n' });
}

const Server = struct {
    allocator: std.mem.Allocator,
    address: std.net.Address,
    server: std.net.StreamServer,
    topics: std.AutoHashMap(u8, std.ArrayList(Client)),

    fn init(alloc: Allocator) !Server {
        const address = std.net.Address.initIp4([4]u8{ 127, 0, 0, 1 }, 2257);
        const list = std.AutoHashMap(u8, std.ArrayList(Client)).init(alloc);
        var server = std.net.StreamServer.init(.{ .reuse_address = true });

        try server.listen(address);
        return Server{
            .address = address,
            .server = server,
            .allocator = alloc,
            .topics = list,
        };
    }

    fn deinit(self: *Server) void {
        var topics_iterator = self.topics.valueIterator();
        while (topics_iterator.next()) |*val| {
            val.*.*.deinit();
        }

        self.server.deinit();
    }

    fn run(self: *Server) !void {
        var data: []const u8 = undefined;
        const conn = try self.server.accept();
        while (true) {
            var buf: [1000]u8 = undefined;
            const dataSize = try conn.stream.read(buf[0..]);
            data = trimWhitespaces(buf[0..dataSize]);

            std.log.debug("got data from client ({s}), parsing", .{data});

            const msg = std.json.parseFromSlice(JoinTopicMessage, self.allocator, data, .{}) catch {
                continue; // keep waiting until theres valid JSON
            };
            defer msg.deinit();

            std.log.debug("registering the client to the topic", .{});
            const clientRepr = Client{
                .stream = conn.stream,
                .addr = conn.address,
                .nick = msg.value.player_nick,
                .score = 0,
                .done_til = 0,
            };

            var entry = try self.topics.getOrPutValue(msg.value.top_id, std.ArrayList(Client).init(self.allocator));
            var arr: *std.ArrayList(Client) = entry.value_ptr;
            try arr.*.append(clientRepr);

            std.log.debug("registered client: {any}", .{clientRepr});
        }
        conn.stream.close();
    }
};

pub fn main() !void {
    var gpalloc = std.heap.GeneralPurposeAllocator(.{}){};
    var server = try Server.init(gpalloc.allocator());
    defer server.deinit();

    try server.run();
}
