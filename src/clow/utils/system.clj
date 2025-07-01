(ns clow.utils.system
  (:require 
   [clow.components.sample :as sample]
   [clow.components.router :as router]
   [clow.components.webserver :as webserver]
   [com.stuartsierra.component :as component]
))

(defn new-system [routertype]
  (component/system-using
   (component/system-map
    :sample    (sample/->SampleComponent)
    :router    (router/map->Router {:type routertype})
    :webserver (webserver/map->WebServer {:type routertype}))
   
   {:webserver [:router]}))