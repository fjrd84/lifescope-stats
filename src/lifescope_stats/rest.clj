(ns lifescope-stats.rest
  (:use [compojure.core] 
        [ring.middleware.json]) 
  (:require [compojure.handler :as handler] 
            [ring.util.response :refer [response]]
            [clojurewerkz.elastisch.rest :as esr]
            [compojure.route :as route]))

; Read the configuration file
(def config (read-string (slurp "config.clj")))

(let [conn (esr/connect (:url (:elastic config))
                        {:basic-auth [(:user (:elastic config))
                                      (:password (:elastic config))]})])

(defn- str-to [num]
  (apply str (interpose ", " (range 1 (inc num)))))

(defn- str-from [num]
  (apply str (interpose ", " (reverse (range 1 (inc num))))))

(defroutes app-routes
  (GET "/" [] (response {:message "Lifescope Stats API"}))
  (GET "/search/:word" [to] (str-to (Integer. to)))
  (route/not-found
   (response {:message "Page not found"})))

; Middleware: logger
(defn wrap-log-request [handler]
  (fn [req]
    (println req)
    (handler req)))

(def app
  ( -> app-routes
       wrap-log-request
       wrap-json-response
       wrap-json-body
       ))


