(ns lifescope-stats.rest
  (:use [compojure.core] 
        [ring.middleware.json]) 
  (:require [compojure.handler :as handler] 
            [ring.util.response :refer [response]]
            [clojurewerkz.elastisch.rest :as esr]
            [clojurewerkz.elastisch.rest.document :as esd]
            [clojurewerkz.elastisch.query :as q]
            [clojurewerkz.elastisch.rest.response :as esrsp]
            [clojure.pprint :as pp]
            [compojure.route :as route]))

; Read the configuration file
(def config (read-string (slurp "config.clj")))

(def conn (esr/connect (:url (:elastic config))
                       {:basic-auth [(:user (:elastic config))
                                     (:password (:elastic config))]}))

(defn testme [] (let [res  (esd/search conn "analysis" "" :query (q/term :source "twitter"))
                      n    (esrsp/total-hits res)
                      hits (esrsp/hits-from res)]
                  (println (format "Total hits: %d" n))
                  (pp/pprint hits)))

(defn- str-to [num]
  (apply str (interpose ", " (range 1 (inc num)))))

(defn- str-from [num]
  (apply str (interpose ", " (reverse (range 1 (inc num))))))

(defroutes app-routes
  (GET "/" [] (response {:message "Lifescope Stats API"}))
  (GET "/search/:word" [word]
       (response (esd/search conn "analysis" :query
                             (q/term :source word))))
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


