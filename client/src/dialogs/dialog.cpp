#include "dialog.hpp"

namespace dialogs {

void Dialog::render() {
    if (!this->shouldRender())
        return;

    if (this->darken)
        this->darkeningRect.Draw(this->darkeningColor);
}

} // namespace dialogs