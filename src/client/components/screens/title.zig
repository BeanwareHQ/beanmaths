const std = @import("std");
const rl = @import("raylib");
const rgui = @import("raygui");
const types = @import("../../types.zig");
const IconText = @import("../../util.zig").IconText;
const Icons = rgui.GuiIconName;

const rects = .{
    .dummyRec = rl.Rectangle{ .x = 296, .y = 72, .width = 776, .height = 264 },
    .playButton = rl.Rectangle{ .x = 520, .y = 608, .width = 328, .height = 48 },
    .settingsButton = rl.Rectangle{ .x = 520, .y = 664, .width = 80, .height = 40 },
    .exitButton = rl.Rectangle{ .x = 608, .y = 664, .width = 152, .height = 40 },
    .aboutButton = rl.Rectangle{ .x = 768, .y = 664, .width = 80, .height = 40 },
};

const Props = struct {
    screenWidth: i32,
    screenHeight: i32,
};

const ButtonStates = struct {
    aboutButton: bool,
    settingsButton: bool,
    playButton: bool,
    exitButton: bool,

    fn init() @This() {
        return @This(){
            .aboutButton = false,
            .settingsButton = false,
            .playButton = false,
            .exitButton = false,
        };
    }
};

pub const TitleScreen = struct {
    componentProps: Props,
    shouldDeinit: bool,
    screenTo: types.GameScreen,

    buttonStates: ButtonStates,

    pub fn init(props: Props) @This() {
        return @This(){ .componentProps = props, .shouldDeinit = false, .screenTo = types.GameScreen.exit, .buttonStates = ButtonStates.init() };
    }

    pub fn deinit(self: *@This()) void {
        _ = self;
    }

    pub fn render(self: *@This()) void {
        rl.ClearBackground(rl.RAYWHITE);

        // Other stuff
        _ = rgui.GuiDummyRec(rects.dummyRec, "BeanMaths Logo");

        // Buttons
        self.buttonStates.playButton = (rgui.GuiButton(rects.playButton, IconText(Icons.ICON_PLAYER_PLAY, "Play")) != 0);
        self.buttonStates.settingsButton = (rgui.GuiButton(rects.settingsButton, IconText(Icons.ICON_GEAR, "Settings")) != 0);
        self.buttonStates.aboutButton = (rgui.GuiButton(rects.aboutButton, IconText(Icons.ICON_INFO, "About")) != 0);
        self.buttonStates.exitButton = (rgui.GuiButton(rects.exitButton, IconText(Icons.ICON_EXIT, "Quit")) != 0);
    }

    pub fn refresh(self: *@This(), updateProps: anytype) void {
        _ = updateProps;

        if (self.buttonStates.playButton)
            std.debug.print("play button pressed", .{});
        if (self.buttonStates.aboutButton)
            std.debug.print("about button pressed", .{});
        if (self.buttonStates.settingsButton)
            std.debug.print("settings button pressed", .{});
        if (self.buttonStates.exitButton)
            std.debug.print("exit button pressed", .{});
    }
};
