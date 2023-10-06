const App = @import("./app.zig").App;

pub fn main() !void {
    const app = App.init();
    defer app.deinit();

    app.run();
}
