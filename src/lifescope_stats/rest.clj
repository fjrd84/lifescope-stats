(ns lifescope-stats.rest
  (:use [compojure.core]
        [ring.middleware.json])
  (:require [compojure.handler :as handler]
            [lifescope-stats.stats :refer :all]
            [ring.util.response :refer [response]]
            [compojure.route :as route]))

(defroutes app-routes
  (GET "/" [] (response {:message "Lifescope Stats API"
                         :messages-count (total-count)}))
  (GET "/queries" [word]
       (response (all-queries-count)))
  (GET "/problems" []
       (response (find-all-problems)))
  (GET "/solutions/:problem" [problem]
       (response (find-solutions-to-problem problem)))
  (GET "/messages/search/:word" [word]
       (response (match-search word)))
  (GET "/messages/match/:problem/:solution" [problem solution]
       (response (find-problem-solution-matches problem solution)))
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

