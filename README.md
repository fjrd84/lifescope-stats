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


## License

Copyright © 2017

Distributed under the Eclipse Public License either version 1.0.
