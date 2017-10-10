(ns lifescope-stats.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojurewerkz.elastisch.rest :as esr]
            [lifescope-stats.rest :refer :all]
            [ring.adapter.jetty :as jetty]))


(defn -main []
  (let [conn (esr/connect "http://127.0.0.1:9200"
                          {:basic-auth ["elastic" "changeme"]})])
  (jetty/run-jetty app {:port 7000}))
