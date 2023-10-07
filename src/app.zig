const std = @import("std");
const rl = @import("raylib");
const rgui = @import("raygui");

const Button = struct {
    bounds: rl.Rectangle,
    updateFunc: ?*const fn (type) void,
    updateFuncParams: type,

    pub fn init(boundsRect: rl.Rectangle, text: []const u8) Button {
        Button{ .bounds = boundsRect, .text = text };
    }

    pub fn refresh(self: *const Button) void {
        if (rgui.GuiButton(self.bounds, self.text) != 0) {
            if (self.updateFunc != null) {
                self.updateFunc.?(self.updateFuncParams.?);
            }
        }
    }
};

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
