#include "raygui.h"
#include "screen.hpp"

namespace screens {

void TitleScreen::render() {
    if (!this->shouldRender())
        return;

    GuiDummyRec(this->dummyRec, "BeanMaths Logo Placeholder");

    this->playButton.render();
    this->settingsButton.render();
    this->exitButton.render();
    this->aboutButton.render();
}

void TitleScreen::update(long gtState) {
    if (!this->shouldUpdate())
        return;

    if (this->playButton.update(gtState))
        return;
    if (this->settingsButton.update(gtState))
        return;
    if (this->exitButton.update(gtState))
        return;
    if (this->aboutButton.update(gtState))
        return;
}

} // namespace screens