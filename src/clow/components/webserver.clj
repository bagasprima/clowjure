(ns clow.components.webserver
  (:require
   [com.stuartsierra.component :as component]
   [io.pedestal.http :as http]
   [ring.adapter.jetty :refer [run-jetty]]))

(defrecord WebServer [router type server]
  component/Lifecycle

  (start [this]
    (let [handler (:handler router)
          srv (case type
                :pedestal
                (do
                  (println "Starting Pedestal")
                  (assoc this :server (http/start handler)))

                ;; Ring-compatible (Compojure, Reitit)
                (do
                  (println "Starting Jetty with Ring handler")
                  (assoc this :server (run-jetty handler {:port 3000 :join? false}))))]
      srv))

  (stop [this]
    (when-let [srv (:server this)]
      (println "Stopping server")
      (if (= type :pedestal)
        (http/stop srv)
        (.stop srv)))
    (assoc this :server nil)))
