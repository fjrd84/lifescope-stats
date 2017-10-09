(ns lifescope-stats.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [lifescope-stats.rest :refer :all]
            [ring.adapter.jetty :as jetty]))


(defn -main []
  (jetty/run-jetty app {:port 3000}))
