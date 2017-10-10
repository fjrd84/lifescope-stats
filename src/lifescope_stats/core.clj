(ns lifescope-stats.core
  (:require [clojurewerkz.elastisch.rest :as esr]
            [lifescope-stats.rest :refer :all]
            [ring.adapter.jetty :as jetty]))


(defn -main []
  (let [conn (esr/connect "http://127.0.0.1:9200"
                          {:basic-auth ["elastic" "changeme"]})])
  (jetty/run-jetty app {:port 7000}))
