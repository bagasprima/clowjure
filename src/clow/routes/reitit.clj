(ns clow.routes.reitit
  (:require [reitit.ring :as ring]
            [ring.util.response :as resp]))

(defn build []
  (ring/ring-handler
   (ring/router
    [["/" {:get (fn [_] (resp/response "Reitit: Home"))}]
     ["/todo" {:post (fn [_] (resp/response "Reitit: Todo"))}]])))