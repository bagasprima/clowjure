(ns clow.routes
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :refer [redirect]]
            ;; [to_do_app_clojure.state :as state]
            ;; [to_do_app_clojure.views :as views]
            [cheshire.core :as json]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] (#(apply str ["hello" "world" "from" %]) "/"))
  (GET "/ipsum/:myparam" [myparam :as request]
       (apply str ["hello " "world " "from " myparam])))

(route/not-found "not found")

(def app
  (wrap-defaults app-routes (assoc-in site-defaults [:security :anti-forgery] false)))
