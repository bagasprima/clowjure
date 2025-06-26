(ns clow.routes
  (:require
   [clojure.java.io]
   [clow.handler :as handler]
   [compojure.core :refer [defroutes GET POST]]
   [compojure.route :as route]
   [net.cgrand.enlive-html :as html]
   [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
   [ring.util.response :as resp]
   [clow.db :as db]))

(def template-path "templates/index.html")

;; experimental
(defn render-todos-page [todos]
  (let [template (html/html-resource (clojure.java.io/resource template-path))]
    (html/emit*
     (html/at template
              [:#todo-list]
              (html/content
               (for [todo todos]
                 (html/at
                  (html/select template [:.todo-item])
                  [:.todo-title] (html/content (:task todo))
                  ;; Point the form action at /todo/{id}/done
                  [:.complete-form] (html/set-attr :action (str "/todo/" (:id todo) "/done"))
                  ;; If it's already done, hide the button
                  [:.complete-form] (if (:done todo)
                                      (html/set-attr :style "display:none;")
                                      identity))
                 ))))))

(defroutes app-routes
  (GET "/" [] {:status 200
               :headers {"Content-Type" "text/html"}
               :body (apply str (render-todos-page (db/get-todos)))})
  (GET "/ipsum/:myparam" [myparam]
    ; myparam needs to be the same name
    (apply str ["hello " "world " "from " myparam]))
  (GET "/another/:a/:b/:c/:d" [a b d e]
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
    (handler/add-todo-handler task)
    (resp/redirect "/"))
  (POST "/todo/add" [task]
    (println "Adding todo:" task)
    (handler/add-todo-handler task)
    (resp/redirect "/"))
  (GET "/todo/:id/done" [id]
    ; this will expectedly throw error when parseint error
    (let [int-id (Integer/parseInt id)]
      (println "/todo/:id/done" int-id)
      (handler/complete-todo-handler int-id)))
  (POST "/todo/:id/done" [id]
      ; this will expectedly throw error when parseint error
    (let [int-id (Integer/parseInt id)]
      (println "/todo/:id/done" int-id)
      (handler/complete-todo-handler int-id)
      (resp/redirect "/")))
  (route/not-found
   (->
    (resp/not-found "Not found!")
    (resp/content-type "text/html;charset=utf-8"))))

(def app
  (wrap-defaults app-routes (assoc-in site-defaults [:security :anti-forgery] false)))
