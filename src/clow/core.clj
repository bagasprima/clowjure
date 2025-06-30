(ns clow.core
  (:gen-class)
  (:import 
   [sun.misc Signal SignalHandler])
  (:require
   [ring.adapter.jetty :refer [run-jetty]]
   [clow.routes.jetty]))

(defonce server (atom nil))

(defn start-server []
  (println "Starting server...")
  (let [serv (run-jetty clow.routes.jetty/approutes {:port 3000 :join? false})]
    (reset! server serv)))

(defn stop-server []
  (when-let [s @server]
     (println "Stopping server...")
     (.stop s)
     (reset! server nil)))

(defn restart-server []
  (try
    (stop-server)
    (Thread/sleep 500) ;; Wait briefly for port to be released
    (start-server)
    (println "Successfully restarted.")
    (catch Exception e
      (println "Failed to restart server:" (.getMessage e))
      (.printStackTrace e))))

(defn add-restart-signal-handler []
  ;; Replace "HUP" with "USR1" or "USR2" if needed
  (Signal/handle (Signal. "HUP")
                 (reify SignalHandler
                   (handle [_ sig]
                     (println "Received signal:" (.getName sig))
                     (restart-server)))))


(defn -main []
  (start-server)
  (add-restart-signal-handler)
  (println "Ready to receive SIGHUP...")
  @(promise))