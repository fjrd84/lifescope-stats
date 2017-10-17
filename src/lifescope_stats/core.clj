(ns lifescope-stats.core
  (:require [lifescope-stats.rest :refer :all]
            [ring.adapter.jetty :as jetty]))

(defn -main []
  (jetty/run-jetty app {:port (:port config)}))
