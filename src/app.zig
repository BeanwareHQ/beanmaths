const std = @import("std");
const rl = @import("raylib");
const rgui = @import("raygui");

pub const App = struct {
    pub fn init() App {
        return App{};
    }

    pub fn deinit(self: *const App) void {
        _ = self;
    }

    fn refresh(self: *const App) void {
        _ = self;
    }

    fn render(self: *const App) void {
        rl.ClearBackground(rl.RAYWHITE);
        rl.DrawText("FooBar", 20, 20, 20, rl.BLUE);
        _ = self;
    }

    pub fn run(self: *const App) void {
        rl.InitWindow(800, 600, "arstoylnafduyokawft");
        rl.SetTargetFPS(60);

        defer rl.CloseWindow();

        while (!rl.WindowShouldClose()) {
            self.refresh();
            rl.BeginDrawing();
            self.render();
            rl.EndDrawing();
        }
    }
};
