#pragma once

#include "../components/component.hpp"

namespace screens {

class Screen : public components::Component {
  protected:
    int windowWidth;
    int windowHeight;

  public:
    Screen(int windowWidth, int windowHeight) {
        this->windowWidth = windowWidth;
        this->windowHeight = windowHeight;
    }
};

} // namespace screens