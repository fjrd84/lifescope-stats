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
  (GET "/histogram/days/" []
       (response (days-histogram)))
  (GET "/histogram/weeks/" []
       (response (weeks-histogram)))
  (GET "/histogram/months/" []
       (response (months-histogram)))
  (GET "/queries/" []
       (response (find-all-queries)))
  (GET "/problems/" []
       (response (find-all-problems)))
  (GET "/solutions/:problem/" [problem]
       (response (find-solutions-to-problem problem)))
  (GET "/messages/search/:word/" [word]
       (response (match-search word)))
  (GET "/messages/match/:problem/:solution/" [problem solution]
       (response (find-problem-solution-matches problem solution)))
  (route/not-found
   (response {:message "Page not found"})))

(defn wrap-log-request 
  "Middleware: logger"
  [handler]
  (fn [req]
    (println req)
    (handler req)))

(defn allow-cors
  "Middleware to allow cross origin requests"
  [handler]
  (fn [request]
    (let [response (handler request)]
      (-> response
          (assoc-in [:headers "Access-Control-Allow-Origin"] "*")
          (assoc-in [:headers "Access-Control-Allow-Methods"] "GET,OPTIONS")
          (assoc-in [:headers "Access-Control-Allow-Headers"] "X-Requested-Widh,Content-Type,Cache-Control")
          ))))


(def app
  (-> app-routes
      allow-cors
      wrap-log-request
      wrap-json-response
      wrap-json-body))

