(defproject brian-penguin "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [compojure "1.6.1"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [environ "1.1.0"]]
  :min-lein-version "2.0.0"
  :repl-options {:init-ns brian-penguin.web}
  :plugins [[environ/environ.lein "0.3.1"]]
  :hooks [environ.leiningen.hooks]
  :uberjar-name "brian-penguin-standalone.jar"
  :profiles {:production {:env {:production true}}})
