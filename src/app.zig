const std = @import("std");
const rl = @import("raylib");
const rgui = @import("raygui");

pub fn Button(comptime P: type) type {
    const UpdateFuncType = ?*const fn (?P) void;
    return struct {
        text: [*:0]const u8,
        bounds: rl.Rectangle,
        updateFunc: UpdateFuncType,

        pub fn init(boundsRect: rl.Rectangle, text: [*:0]const u8, updateFunc: UpdateFuncType) Button(P) {
            return Button(P){
                .bounds = boundsRect,
                .text = text,
                .updateFunc = updateFunc,
            };
        }

        pub fn render(self: *const Button(P), param: ?P) void {
            if (rgui.GuiButton(self.bounds, self.text) != 0) {
                if (self.updateFunc != null) {
                    self.updateFunc.?(param);
                }
            }
        }
    };
}

fn initRectangle(x: f32, y: f32, w: f32, h: f32) rl.Rectangle {
    return rl.Rectangle{ .x = x, .y = y, .width = w, .height = h };
}

pub const App = struct {
    button: Button(void),

    fn buttonUpdate(a: ?void) void {
        _ = a;
        std.io.getStdOut().writer().print("weewooo\n", .{}) catch {
            return;
        };
    }

    pub fn init() App {
        var button = Button(void).init(initRectangle(20, 20, 200, 120), "wee", buttonUpdate);
        return App{ .button = button };
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
        self.button.render(null);
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
