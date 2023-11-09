# BeanMaths

A mental maths practice app, but it's a multiplayer quiz. And very scuffed too!

## Notice

This is the old java codebase. Jaylib was way too crashy in most cases, and I simply was not able to create an app with decent quality in time for submission, and I instead needed an extension for the actual graded part. Oh well!

There will be a new codebase, either in Rust or C++ with bevy or raylib (depending on the language) in the near future.

**beanmaths must not die!** 

## Info

This project was made for my school's Computer Science Course. A branch will be created in the future as an archive for the version that was put up for submission. PRs and MRs will not be accepted before the submission is made.

The server is written in Zig, and the client is written in Java.

## License

Due to the license of the Raylib Java bindings used ([jaylib](https://github.com/electronstudio/jaylib)) and its license (GPLv3 + CE, which disallows linking Java classes with code not licensed under the GPL), the client part is licensed under the GNU GPL version 3 and later, and its full license text can be found [here](/client/LICENSE.md). The server is licensed under the MIT license (full text can be found [here](/server/LICENSE.md)).