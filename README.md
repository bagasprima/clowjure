# clow (or Clowjure)

It's a simple app for learning clojure.  
It's simple.  
It's fun.  
It's not serious.


## How to Run

### Requirements
This project includes a Makefile to simplify common tasks.  
Make sure the following are installed:
- Leiningen
- Java JDK 17 or newer
- make (available by default on macOS and most Linux distros)

Install Leiningen on macOS:

```bash
brew install leiningen
```

---
#### 📦 Build the App
To compile and package the app into a standalone JAR:

```bash
make build
```

> _This will run lein uberjar and generate the JAR at target/clow-0.1.0-standalone.jar._

---
#### 🚀  Run the App

To compile and run locally:
```bash
make run
```

## License

Copyright © 2025 bagasprima

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
