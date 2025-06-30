(ns clow.routes.jetty
  (:require
   [compojure.core :refer [defroutes GET POST]]
   [compojure.route :as route]
   [clow.handlers.simple]
   [clow.handlers.todo]
   [ring.util.response :as resp]))

(defroutes approutes
  ;; To-do List VIEW routes -- handles front-end
  (GET "/" [] 
    (clow.handlers.todo/get-view))

  ;; To-do List API routes
  (GET "/todo" []
    (println "/todo")
    (clow.handlers.todo/get-todo))

  (GET "/todo/add/:task" [task]
    (println "/todo/add/:task" task)
    (clow.handlers.todo/add-todo task)
    (resp/redirect "/"))

  (POST "/todo/add" [task]
    (println "Adding todo:" task)
    (clow.handlers.todo/add-todo task)
    (resp/redirect "/"))

  (GET "/todo/:id/done" [id]
    ; this will expectedly throw error when parseint error
    (let [int-id (Integer/parseInt id)]
      (println "/todo/:id/done" int-id)
      (clow.handlers.todo/complete-todo int-id)))

  (POST "/todo/:id/done" [id]
      ; this will expectedly throw error when parseint error
    (let [int-id (Integer/parseInt id)]
      (println "/todo/:id/done" int-id)
      (clow.handlers.todo/complete-todo int-id)
      (resp/redirect "/")))

  ;; TESTING routes -- any playable api goes here
  (GET "/ipsum/:myparam" [myparam]
    ; myparam needs to be the same name with the :<name>
    (apply str ["hello " "world " "from " myparam]))
  (GET "/another/:a/:b/:c/:d" [a b d e]
    ; magically it maps with the naming, instead of ordered
    (println a b e d)
    (apply str ["hello " "world " "from " a "-" b "-" e "-" d]))
  (GET "/simple/:inquiry" [inquiry]
    (println "/simple/:inquiry" inquiry)
    (->
     (clow.handlers.simple/answer-this inquiry)
     (resp/response)
     (resp/status 200)
     (resp/content-type "text/html;charset=utf-8")))

  ;; FALLBACK routes
  (route/not-found
   (->
    (resp/not-found "Not found!")
    (resp/content-type "text/html;charset=utf-8"))))
