(ns clow.views.enlive
  (:require 
   [net.cgrand.enlive-html :as html]
   [clojure.java.io]))

(def template-path "templates/index.html")

(defn todo-landing-page [todos]
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
                                      identity))))))))
