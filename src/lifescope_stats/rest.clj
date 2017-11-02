(ns lifescope-stats.rest
  (:use [compojure.core]
        [ring.middleware.json])
  (:require [compojure.handler :as handler]
            [lifescope-stats.stats :refer :all]
            [ring.util.response :refer [response]]
            [compojure.route :as route]))

(defroutes app-routes
  (GET "/" [] (response {:message "Lifescope Stats API"}))
  (GET "/query-count/" [word]
       (response (all-queries-count)))
  (GET "/search/:word" [word]
       (response (wildcard-search word)))
  (route/not-found
   (response {:message "Page not found"})))

;; Middleware: logger
(defn wrap-log-request [handler]
  (fn [req]
    (println req)
    (handler req)))

(def app
  (-> app-routes
      wrap-log-request
      wrap-json-response
      wrap-json-body))

