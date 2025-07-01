(ns clow.routes.pedestal
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [ring.util.response :as resp]))

(defn build []
  ;; Return a Pedestal service map instead of Ring handler
  {:env :prod
   ::http/routes
   (route/expand-routes
    #{["/" :get (fn [_] {:status 200 :body "Pedestal: Home"}) :route-name :home]
      ["/todo" :post (fn [_] {:status 200 :body "Pedestal: Todo"}) :route-name :todo]})
   ::http/type :jetty
   ::http/port 3000
   ::http/router :linear-search})