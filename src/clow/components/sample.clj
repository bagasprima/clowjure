(ns clow.components.sample
  (:require 
   [com.stuartsierra.component :as component]))

(defrecord SampleComponent []
  component/Lifecycle

  (start [this]
    (println ";; Starting ExampleComponent")
    ;; In the 'start' method, a component may assume that its
    ;; dependencies are available and have already been started.
    (assoc this :status "active"))

  (stop [this]
    (println ";; Stopping ExampleComponent")
    (assoc this :status "inactive")
    ;; Likewise, in the 'stop' method, a component may assume that its
    ;; dependencies will not be stopped until AFTER it is stopped.
    this))