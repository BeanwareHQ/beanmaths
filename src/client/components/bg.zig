const rl = @import("raylib");
const rgui = @import("raygui");
const std = @import("std");

pub const Props = struct {
    screenWidth: f32,
    screenHeight: f32,
};

pub const Background = struct {
    props: Props,
    shouldDeinit: bool,
    rect: rl.Rectangle,
    targetColor: rl.Color,
    overlayRect: rl.Rectangle,
    overlayColor: rl.Color,

    const currOpacity = 255;
    const totalFrames: u32 = 60 * 6; // frames per second * no of seconds
    const opacityDelta: f32 = totalFrames / currOpacity;

    pub fn init(props: Props) @This() {
        return @This(){
            .props = props,
            .shouldDeinit = false,
            .rect = rl.Rectangle{ .x = 0, .y = 0, .width = props.screenWidth, .height = props.screenHeight },
            .targetColor = rl.GetColor(0xffeef3ff),
            .overlayRect = rl.Rectangle{ .x = 0, .y = 0, .width = props.screenWidth, .height = props.screenHeight },
            .overlayColor = rl.RAYWHITE,
        };
    }

    pub fn render(self: *@This()) void {
        rl.DrawRectangleRec(self.rect, self.targetColor);
        rl.DrawRectangleRec(self.overlayRect, self.overlayColor);
    }

    pub fn refresh(self: *@This(), updateParams: anytype) void {
        const animationProgress: u32 = @intCast((updateParams.globTimer + totalFrames) % totalFrames);
        const halfTotalFrames = totalFrames / 2;

        if (animationProgress < halfTotalFrames) {
            self.overlayColor.a -%= @as(u8, opacityDelta);
        } else if (animationProgress > halfTotalFrames) {
            self.overlayColor.a +%= @as(u8, opacityDelta);
        }
    }
};
