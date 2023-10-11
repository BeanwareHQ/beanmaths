const rl = @import("raylib");

pub fn main() void {
    rl.InitWindow(1200, 720, "BeanMaths");
    defer rl.CloseWindow();
    rl.SetTargetFPS(60);

    while (!rl.WindowShouldClose()) {
        rl.BeginDrawing();
        defer rl.EndDrawing();

        rl.ClearBackground(rl.RAYWHITE);
    }
}
