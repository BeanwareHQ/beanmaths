#include <memory>
#include <utility>
#include <vector>

#include "navigationcontroller.hpp"
#include "src/components/component.hpp"
#include "src/dialogs/dialog.hpp"
#include "src/screens/screen.hpp"

namespace navigation {

screens::Screen& NavigationController::back() const {
    return (*this->screens.back());
}

bool NavigationController::empty() const { return this->screens.empty(); }

void NavigationController::add(std::unique_ptr<screens::Screen>&& screen) {
    if (!this->screens.empty()) {
        (*this->screens.back()).componentState =
            components::ComponentState::DISABLED;
    }

    screen->componentState = components::ComponentState::ENABLED;
    this->screens.push_back(std::move(screen));
}

void NavigationController::add(std::unique_ptr<dialogs::Dialog>&& dialog) {
    if (!this->screens.empty()) {
        screens::Screen* screen = this->screens.back().get();

        // hacky way to check if a class is an instance of a possible subclass
        try {
            // Fully disable the previous item if it is a dialog.
            dialogs::Dialog* previousDialog =
                dynamic_cast<dialogs::Dialog*>(screen);

            dialog->darken = false; // the dialog is already darkened, so no
                                    // need to do it again.

            previousDialog->componentState =
                components::ComponentState::DISABLED;
        } catch (std::bad_cast& exc) {
            // The previous item is not a dialog, so allow darkening.
            dialog->darken = true;

            // allow the previous dialog to be rendered but not updated. Done so
            // that the screen does not appear locked up
            screen->componentState = components::ComponentState::NOUPDATE;
        }
    }

    dialog->componentState = components::ComponentState::ENABLED;

    // move the data into the vector
    this->screens.push_back(std::move(dialog));
}

std::unique_ptr<screens::Screen> NavigationController::pop() {
    std::unique_ptr<screens::Screen> result = std::move(this->screens.back());
    this->screens.pop_back();
    return result;
}

auto NavigationController::getScreens()
    -> std::vector<std::unique_ptr<screens::Screen>>& {
    return this->screens;
}

} // namespace navigation