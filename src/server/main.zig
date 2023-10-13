const std = @import("std");
const net = std.net;

const Server = @import("./server.zig").Server;

const VERSION = "0.1.0";

pub fn main() !void {
    std.log.info("Starting BeanServer version {s}...", .{VERSION});

    // set up allocator early
    var gpalloc = std.heap.GeneralPurposeAllocator(.{}){};
    const allocator = gpalloc.allocator();

    // get the address from argv
    const args = try std.process.argsAlloc(allocator);
    const port = try std.fmt.parseInt(u16, args[2], 10);
    const addr = try net.Address.parseIp4(args[1], port);
    std.process.argsFree(allocator, args);

    // init the server
    var server = try Server.init(allocator, addr);
    defer server.deinit();

    try server.run();
}
