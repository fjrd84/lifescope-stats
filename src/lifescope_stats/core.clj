(ns lifescope-stats.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]))

(use 'ring.adapter.jetty)

(defn app-handler [request]
  {:status 200
   :headers {"Content-Type" "text/plain;=us-ascii"}
   :body (str request)})

(defn -main
  "Start the demo server."
  [& args]
  (run-jetty app-handler {:port 3000}))

