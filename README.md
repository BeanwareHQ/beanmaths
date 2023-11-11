# BeanMaths

A mental maths practice app, but it's a multiplayer quiz. And very scuffed too!

## Info

This project was made for my school's Computer Science Course. A branch will be created in the future as an archive for the version that was put up for submission. PRs and MRs will not be accepted before the submission is made.

The server is written in Rust, and the client in C++.

### Why C++? (for the Rust purists)

One simple answer, **raylib**. Raylib's bindings to rust is very old and pretty much unmaintained, and the raw FFI bindings are unsafe, which will require me to write a wrapper around it, which is just extra work. Therefore, I have chosen to use C++, because it has a nice OO wrapper around raylib, and the language is a bit less painful to work with than C (generics are macros, no runtime polymorphism, no namespaces, really annoying strings).

*I can also use this as an excuse to say that I have C++ experience. Don't tell anyone!*

## License

This project is licensed under the Mozilla Public License, version 2. You can find the full license test [here.](/LICENSE.md)