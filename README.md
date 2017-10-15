# lifescope-stats

Stats module for the [lifescope-project](https://www.lifescope-project.com).

## Installation

You need [Leiningen](https://leiningen.org/) to run, build and test this project.

## Build it

Build the project with the following command:

    lein uberjar 

It will generate a `.jar` file into the subdirectory `./target/uberjar/`.

## Run it 

Run the standalone jar file:

    java -jar lifescope-stats-0.1.0-standalone.jar

Alternatively, you can directly run the project using leiningen with the following command:

    lein run

### Start the web server

Type this command on the shell: `lein ring server-headless`.

## Quality indicators


- `lein eastwood` run the linter
- `lein cloverage` analyze the test coverage
- `lein ancient` search for outdated dependencies
- `lein kibit` analyze the code and suggest more idiomatic variants
- `lein bikeshed` analyze the formatting of the source files
- `lein omni` run all the quality analyzers (eastwood, cloverage, ancient, kibit and bikeshed).


## License

Copyright © 2017

Distributed under the Eclipse Public License either version 1.0.
