project('beanmaths-client', 'cpp',
  version : '0.1.0',
  license: 'MPL-2.0',
  default_options : {
    'warning_level': '3',
    'cpp_std': 'c++20',
  })

# raylib
inc_dirs = include_directories('./raylib-cpp/include')

sources = [
  # src/
  'src/main.cpp',
  'src/app.cpp',

  # src/navigation
  'src/navigation/navigationcontroller.cpp',

  # src/screens
  'src/screens/screen.cpp',

  # src/components
  'src/components/component.cpp',
]

cpp_flags = []

if build_machine.system() == 'darwin'
  cpp_flags += [
  ]
endif

dependencies = [
  dependency('raylib', version: '>=4.5.0')
]

executable('beanmaths', sources,
  include_directories: inc_dirs,
  dependencies: dependencies,
  cpp_args: cpp_flags)
