const std = @import("std");
const net = std.net;
const mem = std.mem;
const Allocator = std.mem.Allocator;
const Thread = std.Thread;

const serverTypes = @import("./types.zig");

fn trimWhitespaces(data: []const u8) []const u8 {
    return mem.trim(u8, data, &[3]u8{ ' ', '\r', '\n' });
}

pub const Server = struct {
    allocator: std.mem.Allocator,
    address: net.Address,
    server: net.StreamServer,
    lobbies: std.AutoHashMap(u8, std.ArrayList(serverTypes.Client)),
    threads: std.ArrayList(Thread),

    pub fn init(alloc: Allocator, address: net.Address) !@This() {
        const lobbies = std.AutoHashMap(u8, std.ArrayList(serverTypes.Client)).init(alloc);
        const threads = std.ArrayList(Thread).init(alloc);
        var server = net.StreamServer.init(.{ .reuse_address = true });

        std.log.info("listening on {any}", .{address});

        try server.listen(address);
        return Server{
            .address = address,
            .allocator = alloc,
            .server = server,
            .lobbies = lobbies,
            .threads = threads,
        };
    }

    pub fn deinit(self: *@This()) void {
        std.log.info("shutting down...", .{});
        var lb_iterator = self.lobbies.valueIterator();

        // deinit values
        while (lb_iterator.next()) |*val| {
            val.*.*.deinit();
        }

        // kill threads
        for (self.threads.items) |*th| {
            th.*.join();
        }

        self.lobbies.deinit();
        self.server.deinit();
    }

    pub fn run(self: *@This()) !void {
        while (true) {
            const conn = try self.server.accept();
            const thread = try Thread.spawn(.{}, Server.threadFunc, .{ self, conn });
            try self.threads.append(thread);
        }
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

    fn acceptConnection(self: *@This(), conn: net.StreamServer.Connection) !void {
        var buf: [1000]u8 = undefined;
        var data: []const u8 = undefined;
        var dataSize: usize = undefined;

        const msg: serverTypes.JoinLobbyRequest = while (true) {
            dataSize = try conn.stream.read(buf[0..]);
            data = trimWhitespaces(buf[0..dataSize]);

            std.log.debug("got data from client ({s}), parsing", .{data});
            const clientResponse = std.json.parseFromSlice(serverTypes.ClientRequest, self.allocator, data, .{}) catch {
                std.log.debug("data recieved was invalid!", .{});
                const resp = serverTypes.Response{ .msg = "Invalid JSON data", .variant = .err_invalid_data };

                // write straight to the stream to avoid an allocation
                try std.json.stringify(resp, .{}, conn.stream.writer());
                continue;
            };

            defer clientResponse.deinit();

            switch (clientResponse.value) {
                .join_lobby => |msg| {
                    clientResponse.deinit();
                    break msg;
                },
                else => {
                    const resp = serverTypes.Response{ .msg = "Bad client response type: must join a lobby first", .variant = .err_bad_response };
                    try std.json.stringify(resp, .{}, conn.stream.writer());
                },
            }
        };

        const lobbyId = msg.lobby_id;

        std.log.debug("registering client to lobby {d}", .{lobbyId});
        var clientRepr = serverTypes.Client{
            .stream = conn.stream,
            .addr = conn.address,
            .nick = msg.player_nick,
            .score = 0,
            .done_til = 0,
        };

        // create the entry if it doesn't exist
        var entry = try self.lobbies.getOrPutValue(lobbyId, std.ArrayList(serverTypes.Client).init(self.allocator));
        var arr: *std.ArrayList(serverTypes.Client) = entry.value_ptr; // for the sake of it
        try arr.*.append(clientRepr);

        std.log.debug("registered client: {any}", .{clientRepr});

        const respMsg = try std.fmt.allocPrint(self.allocator, "successfully registered client to lobby {d}", .{lobbyId});
        const resp = serverTypes.Response{ .msg = respMsg, .variant = .ok };
        try std.json.stringify(resp, .{}, conn.stream.writer());

        try self.handleConnection(&conn);
        conn.stream.close();
    }

    fn threadFunc(self: *@This(), conn: net.StreamServer.Connection) void {
        self.acceptConnection(conn) catch {
            @panic("thread panicked!");
        };
    }
};
