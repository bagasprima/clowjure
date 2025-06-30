(ns clow.routes.pedestal
  (:require
    [com.stuartsierra.component :as component]
    [io.pedestal.http :as http]))

(defrecord Pedestal [service-map service]
  component/Lifecycle
  (start [this]
    (if service
      this
      (assoc
        this
        :service
        (-> service-map
            http/create-server
            http/start))))
  (stop [this]
    (when service
      (http/stop service))
    (assoc this :service nil)))


(defn new-pedestal [service-map]
  (map->Pedestal {:service-map service-map :service nil}))