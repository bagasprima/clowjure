(ns clow.routes.pedestal
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]))

(defn build []
  ;; Return a Pedestal service map instead of Ring handler
  {:env :prod
   ::http/routes
   (route/expand-routes
    #{["/" :get (fn [_] {:status 200 :body "Pedestal: Home"})]
      ["/todo" :post (fn [_] {:status 200 :body "Pedestal: Todo"})]})
   ::http/type :jetty
   ::http/port 3001})