# game-of-life

Game of Life in Clojure mit simpler Swing GUI.

# Continuous Testing konfigurieren

Folgendes in ~/.lein/profiles.clj eintragen bzw. ergänzen:

    {:user {:plugins [[lein-midje "2.0.0-SNAPSHOT"]]
            :repositories {"stuart" "http://stuartsierra.com/maven2"}
            :dependencies [[com.stuartsierra/lazytest "1.2.3"]]}}

# Tests ausführen

    lein midje --lazytest

## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
