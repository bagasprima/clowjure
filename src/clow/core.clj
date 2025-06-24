(ns clow.core
  (:gen-class)
  (:require
   [clojure.string :as str]
   [ring.adapter.jetty :refer [run-jetty]]
   [clow.routes :refer [app]]))

(def saved (atom nil)) ;; Holds the saved value

(def nothing-saved "Nothing saved yet.")

(defn process-input [input]
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

(defn -main []
  (run-jetty app {:port 3000 :join? false?}))

(defn -main-deprecate
  "I am a clowjure waiting for input..."
  [& args]
  (println "Welcome to Clow! Type 'exit' to quit.")
  (loop []
    (print "> ")
    (flush) ;; Make sure prompt is shown immediately
    (let [input (read-line)]
      (if (= input "exit")
        (do (println "Goodbye!") (System/exit 0))
        (do (println (process-input input))
            (recur))))))
