(ns clow.core
  (:gen-class)
  (:import 
   [sun.misc Signal SignalHandler])
  (:require
   [ring.adapter.jetty :refer [run-jetty]]
   [clow.routes.compojure]
   [clow.utils.system]
   [com.stuartsierra.component :as component]
   ))

(def currentrouter :compojure)

; old init
(defonce server-new (atom nil))
(defonce server (atom nil))

(defn start-server []
  (println "Starting server...")
  (let [serv (run-jetty clow.routes.compojure/approutes {:port 3000 :join? false})]
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


; new init
(defn start-system [routertype]
  (let [sys (component/start (clow.utils.system/new-system routertype))]
    (reset! server-new sys)
    (println "✅ System started.")
    sys))

(defn stop-system []
  (when @server-new
    (component/stop @server-new)
    (reset! server-new nil)
    (println "🛑 System stopped.")))

(defn restart-system []
  (try
   (stop-system)
   (Thread/sleep 500) ;; Wait briefly for port to be released
   (start-system currentrouter)
   (println "Successfully restarted.")
   (catch Exception e
     (println "Failed to restart server:" (.getMessage e))
     (.printStackTrace e))))

; SIGHUP interceptor
(defn add-restart-signal-handler []
  ;; Replace "HUP" with "USR1" or "USR2" if needed
  (Signal/handle (Signal. "HUP")
                 (reify SignalHandler
                   (handle [_ sig]
                     (println "Received signal:" (.getName sig))
                     (when @server-new (restart-system))
                     (when @server (restart-server))))))

(defn -main []
  ;; (start-server) ; old router
  (start-system currentrouter) ; new router

  (add-restart-signal-handler)
  (println "Ready to receive SIGHUP...")

  ;; @(promise) is needed to make the program stays alive.
  ;; somehow jetty runs a Thread for this one, but when the jetty is stopped
  ;; (e.g. during restart), the Thread is also stopped.
  @(promise))