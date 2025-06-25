(ns clow.routes
  (:require 
   [clojure.java.io]
   [clow.handler :as handler]
   [compojure.core :refer [defroutes GET]]
   [compojure.route :as route]
   [net.cgrand.enlive-html :as enlive]
   [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
   [ring.util.response :as resp]
   [clow.db :as db]
))

(def template-path "templates/index.html")

(defn render-todos-page [todos]
  (let [template (enlive/html-resource (clojure.java.io/resource template-path))]
    (enlive/emit*
     (enlive/at template
                [:#todo-list]
                (enlive/content
                 (for [todo todos]
                   (enlive/at
                    (enlive/select template [:.todo-item])
                    [:.todo-title] (enlive/content (:title todo))
                    [:.complete-form] (enlive/set-attr :action (str "/todo/" (:id todo) "/done"))
                    [:.complete-form] (if (:done todo)
                                        (enlive/set-attr :style "display:none;")
                                        identity))))))))

(defroutes app-routes
  (GET "/" [] {:status 200
               :headers {"Content-Type" "text/html"}
               :body (apply str (render-todos-page (db/get-todos)))})
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
