const rgui = @import("raygui");

pub fn IconText(i: rgui.GuiIconName, t: [*:0]const u8) [*:0]const u8 {
    return rgui.GuiIconText(@intFromEnum(i), t);
}
