const std = @import("std");
const rl = @import("raylib");
const rgui = @import("raygui");

const Icon = rgui.GuiIconName;

// some constants
const screenWidth = 1360;
const screenHeight = 768;

fn initStyles() void {
    const C = rgui.GuiControl;
    const P = rgui.GuiDefaultProperty;
    rgui.GuiSetStyle(@intFromEnum(C.DEFAULT), @intFromEnum(P.TEXT_SIZE), 15);
}

pub fn main() void {
    rl.InitWindow(screenWidth, screenHeight, "BeanMaths");
    rl.SetTargetFPS(60);
    defer rl.CloseWindow();

    // initialize styles early
    initStyles();

    const components = @import("components/module.zig");

    var globTimer: u32 = 0;
    var screen = components.screens.TitleScreen.init(.{
        .screenWidth = screenWidth,
        .screenHeight = screenHeight,
    });

    while (!rl.WindowShouldClose()) {
        globTimer +%= 1;
        screen.refresh(.{ .globTimer = globTimer });
        if (screen.shouldDeinit)
            std.process.exit(0); // TODO: make it do something based on the screen

        rl.BeginDrawing();
        screen.render();
        rl.EndDrawing();
    }
}
