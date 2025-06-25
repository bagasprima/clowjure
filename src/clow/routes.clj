(ns clow.routes
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [ring.util.response :as resp]
            ;; [to_do_app_clojure.state :as state]
            ;; [to_do_app_clojure.views :as views]
            ;; [cheshire.core :as json]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [clow.handler :as handler]))

(defroutes app-routes
  (GET "/" [] (#(apply str ["hello" "world" "from" %]) "/"))
  (GET "/ipsum/:myparam" [myparam]
    ; myparam needs to be the same name
    (apply str ["hello " "world " "from " myparam]))
  (GET "/another/:a/:b/:c/:d" [a b d e];
    ; magically it maps with the naming, instead of order
    (println a b e d)
    (apply str ["hello " "world " "from " a "-" b "-" e "-" d])) 
  (GET "/simple/:inquiry" [inquiry]
    (println "/simple/:inquiry" inquiry)
    (->
     (handler/simple-handler inquiry)
     (resp/response)
     (resp/status 200)
     (resp/content-type "text/html;charset=utf-8")))
  (GET "/todo" [] 
    (println "/todo")
    (handler/get-todo-handler))
  (GET "/todo/add/:task" [task]
    (println "/todo/add/:task" task)
    (handler/add-todo-handler task))
  (GET "/todo/:id/done" [id]
    ; this will expectedly throw error when parseint error
    (let [int-id (Integer/parseInt id)]
       (println "/todo/:id/done" int-id)
       (handler/complete-todo-handler int-id))) 
  (route/not-found
   (->
    (resp/not-found "Not found!")
    (resp/content-type "text/html;charset=utf-8"))))

(def app
  (wrap-defaults app-routes (assoc-in site-defaults [:security :anti-forgery] false)))
