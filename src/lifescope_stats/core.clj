(ns lifescope-stats.core
  (:require [lifescope-stats.rest :refer :all]
            [ring.adapter.jetty :as jetty]))

(def config (read-string (slurp "config.clj")))

(defn -main []
  (jetty/run-jetty app {:port (:port config)}))
