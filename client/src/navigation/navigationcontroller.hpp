#pragma once

#include <memory>
#include <vector>

// avoids namespace clashes
namespace screens {
class Screen;
}

namespace dialogs {
class Dialog;
}

namespace navigation {

class NavigationController {
public:
    NavigationController() {}
    NavigationController(std::unique_ptr<screens::Screen>&& initialScreen) {
        this->add(std::move(initialScreen));
    }

    screens::Screen& back() const;
    bool empty() const;

    void add(std::unique_ptr<screens::Screen>&& screen);
    void add(std::unique_ptr<dialogs::Dialog>&& dialog);

    auto pop() -> std::unique_ptr<screens::Screen>;
    auto getScreens() -> std::vector<std::unique_ptr<screens::Screen>>&;

private:
    std::vector<std::unique_ptr<screens::Screen>> screens;
};

} // namespace navigation
