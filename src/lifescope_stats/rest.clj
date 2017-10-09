(ns lifescope-stats.rest
  (:use compojure.core
        ring.middleware.json)
  (:require [compojure.handler :as handler] 
            [ring.util.response :refer [response]]
            [compojure.route :as route]))

(defn- str-to [num]
  (apply str (interpose ", " (range 1 (inc num)))))

(defn- str-from [num]
  (apply str (interpose ", " (reverse (range 1 (inc num))))))

(defroutes app-routes
  (GET "/count-up/:to" [to] (str-to (Integer. to)))
  (GET "/count-down/:from" [from] (str-from (Integer. from)))
  (route/not-found
   (response {:message "Page not found"}))
  )

; Middleware: logger
(defn wrap-log-request [handler]
  (fn [req]
    [println req]
    (handler req)))

(def app
  ( -> app-routes
       wrap-log-request
       wrap-json-response
       wrap-json-body
       ))


