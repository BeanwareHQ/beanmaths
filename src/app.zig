const std = @import("std");
const rl = @import("raylib");
const rgui = @import("raygui");

pub const App = struct {
    pub fn init() App {
        return App{};
    }

    pub fn deinit() void {}

    pub fn run() void {}
};
