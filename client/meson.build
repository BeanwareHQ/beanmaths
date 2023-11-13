project('beanmaths-client', 'c', 'cpp',
  version : '0.1.0',
  license: 'MPL-2.0',
  default_options : {
    'warning_level': '3',
    'cpp_std': 'c++20',
    'c_std': 'c17',
  })

# raylib
raylib_dep = dependency('raylib', version: '>=4.5.0')

# raygui
raygui_inc = include_directories('./thirdparty/raygui/src')
raygui_lib = shared_library('raygui', './thirdparty/raygui/src/raygui.c',
  link_language: 'c',
  c_args: ['-DRAYGUI_IMPLEMENTATION'],
  dependencies: [raylib_dep])
raygui_dep = declare_dependency(link_with: raygui_lib,
  include_directories: raygui_inc)

# actual building

inc_dirs = include_directories('./thirdparty', './thirdparty/raylib-cpp/include', './thirdparty/raygui/src')
sources = [
  # src/components
  'src/components/component.cpp',

  # src/config
  'src/config/config.cpp',

  # src/dialogs
  'src/dialogs/dialog.cpp',

  # src/navigation
  'src/navigation/navigationcontroller.cpp',

  # src/rayguiex
  'src/rayguiex/button.cpp',

  # src/screens
  'src/screens/screen.cpp',
  'src/screens/titlescreen.cpp',
  
  # src/
  'src/main.cpp',
  'src/app.cpp', 
]

cpp_flags = []

dependencies = [
  raylib_dep,
  raygui_dep,
]

executable('beanmaths', sources,
  include_directories: inc_dirs,
  dependencies: dependencies,
  cpp_args: cpp_flags)
