(defproject clow "0.1.0"
  :description "a simple clojure project wwwwwwww"
  :url "https://github.com/bagasprima/clowjure"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [io.pedestal/pedestal.service "0.5.10"]
                 [io.pedestal/pedestal.jetty "0.5.10"]
                 [compojure "1.7.0"]
                 [ring/ring-core "1.11.0"]
                 [ring/ring-jetty-adapter "1.9.6"]      
                 [ring/ring-defaults "0.3.4"]
                 [cheshire "6.0.0"]
                 [enlive "1.1.6"]
                 [com.stuartsierra/component "1.1.0"]]
  :main ^:skip-aot clow.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
