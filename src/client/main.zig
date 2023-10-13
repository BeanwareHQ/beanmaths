const std = @import("std");
const rl = @import("raylib");
const rgui = @import("raygui");

const screenWidth = 1360;
const screenHeight = 768;

fn initStyles() void {
    rgui.GuiSetStyle(@intFromEnum(rgui.GuiControl.DEFAULT), @intFromEnum(rgui.GuiDefaultProperty.TEXT_SIZE), 15);
}

fn playButtonClicked() void {
    std.debug.print("play button clicked\n", .{}); // FIXME: placeholder
}

fn settingsButtonClicked() void {
    std.debug.print("settings button clicked\n", .{}); // FIXME: placeholder
}

fn aboutButtonClicked() void {
    std.debug.print("about button clicked\n", .{}); // FIXME: placeholder
}

pub fn main() void {
    rl.InitWindow(screenWidth, screenHeight, "BeanMaths");
    rl.SetTargetFPS(60);
    defer rl.CloseWindow();

    const layoutRecs = .{
        .dummyRect = rl.Rectangle{ .x = 296, .y = 72, .width = 776, .height = 264 },
        .playButton = rl.Rectangle{ .x = 528, .y = 608, .width = 312, .height = 48 },
        .settingsButton = rl.Rectangle{ .x = 528, .y = 664, .width = 152, .height = 40 },
        .aboutButton = rl.Rectangle{ .x = 688, .y = 664, .width = 152, .height = 40 },
    };

    initStyles();

    while (!rl.WindowShouldClose()) {
        rl.BeginDrawing();
        {
            rl.ClearBackground(rl.RAYWHITE); // FIXME: does not stick to theme
            _ = rgui.GuiDummyRec(layoutRecs.dummyRect, "BeanMaths");
            if (rgui.GuiButton(layoutRecs.playButton, "Play") != 0)
                playButtonClicked();
            if (rgui.GuiButton(layoutRecs.settingsButton, "Settings") != 0)
                settingsButtonClicked();
            if (rgui.GuiButton(layoutRecs.aboutButton, "About") != 0)
                aboutButtonClicked();
        }
        rl.EndDrawing();
    }
}
