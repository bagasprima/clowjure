(ns clow.core
  (:gen-class)
  (:require
   [ring.adapter.jetty :refer [run-jetty]]
   [clow.routes :refer [app]]))

(defn -main [] 
  (println "running app")
  (run-jetty app {:port 3000 :join? false?}))
