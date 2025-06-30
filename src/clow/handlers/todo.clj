(ns clow.handlers.todo
  (:require 
   [clow.components.db :as db]
   [clow.views.enlive :as view]
   [cheshire.core :as json]
   ))

(defn get-view []
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (apply str (view/todo-landing-page (db/get-todos)))})

(defn get-todo []
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string (db/get-todos))})

(defn add-todo [task]
  (let [todo (db/add-todo! task)]
    (println "task: " task)
    (println "todo: " todo)
    {:status 201
     :headers {"Content-Type" "application/json"}
     :body (json/generate-string todo)}))

(defn complete-todo [id]
  (let [todo (db/complete-todo! id)]
    (println "id: " id)
    (println "Todo: " todo)
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (json/generate-string todo)}))