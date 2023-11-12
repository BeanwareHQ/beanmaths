#include <iostream>

#include "raygui.h"
#include "screen.hpp"

namespace screens {

void TitleScreen::render() {
    if (!this->shouldRender())
        return;
    std::cout << "arst" << std::endl;
    this->state.aboutButton = GuiButton(
        this->aboutButton, GuiIconText(GuiIconName::ICON_INFO, "About"));
    this->state.playButton = GuiButton(
        this->playButton, GuiIconText(GuiIconName::ICON_PLAYER_PLAY, "Play"));
    this->state.settingsButton = GuiButton(
        this->settingsButton, GuiIconText(GuiIconName::ICON_GEAR, "Settings"));
    this->state.exitButton = GuiButton(
        this->playButton, GuiIconText(GuiIconName::ICON_EXIT, "Quit"));
}

void TitleScreen::update(long gtState) {
    if (!this->shouldUpdate())
        return;

    if (this->state.aboutButton)
        std::cout << "About button clicked" << std::endl;

    if (this->state.exitButton)
        std::cout << "Exit button clicked" << std::endl;

    if (this->state.playButton)
        std::cout << "Play button clicked" << std::endl;

    if (this->state.settingsButton)
        std::cout << "Settings button clicked" << std::endl;
}

} // namespace screens