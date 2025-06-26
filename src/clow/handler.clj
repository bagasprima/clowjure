(ns clow.handler
  (:require [clow.db :as db]
            [cheshire.core :as json]
            [clojure.string :as str]))

(def saved (atom nil)) ;; Holds the saved value
(def nothing-saved "Nothing saved yet.")

(defn get-todo-handler []
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string (db/get-todos))})

(defn add-todo-handler [task]
  (let [todo (db/add-todo! task)]
    (println "task: " task)
    (println "todo: " todo)
    {:status 201
     :headers {"Content-Type" "application/json"}
     :body (json/generate-string todo)}))

(defn complete-todo-handler [id]
  (let [todo (db/complete-todo! id)]
    (println "id: " id)
    (println "Todo: " todo)
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (json/generate-string todo)}))

(defn simple-handler [input]
  (let [input (str/lower-case input)]
    (cond
      (str/blank? input) "type something!"
      (= input "hello") "Hello!!!"
      (= input "bye") "Why you gooooo!!!!"
      (= input "my name") "you are also clowjure"
      (= input "load")
      (if (nil? saved)
        nothing-saved
        (str "your secret is: " @saved))

      (str/starts-with? input "save:")
      (let [value (str/trim (subs input 5))]
        (reset! saved value)
        (str "congrats now i know your secret: " value, ", load it by typing 'load'"))
      :else (format "i reverse curse you: %s" (str/reverse input)))))