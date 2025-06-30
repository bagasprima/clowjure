(ns clow.handlers.simple
  (:require [clojure.string :as str]))

(def saved (atom nil)) ;; Holds the saved value
(def nothing-saved "Nothing saved yet.")

(defn answer-this [input]
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