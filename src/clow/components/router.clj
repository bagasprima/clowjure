(ns clow.components.router
  (:require
   [com.stuartsierra.component :as component]
   [clow.routes.compojure :as compojure-routes]
   [clow.routes.reitit :as reitit-routes]
   [clow.routes.pedestal :as pedestal-routes]
   ))

(defrecord Router [type handler]
  component/Lifecycle

  (start [this]
    (let [h (case type
              :compojure (compojure-routes/build)
              :reitit    (reitit-routes/build)
              :pedestal  (pedestal-routes/build)
              (throw (Exception. (str "Unknown router type: " type))))]
      (assoc this :handler h)))

  (stop [this]
    (assoc this :handler nil)))