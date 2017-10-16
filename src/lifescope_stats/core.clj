(ns lifescope-stats.core
  (:require [clojurewerkz.elastisch.rest :as esr]
            [lifescope-stats.rest :refer :all]
            [ring.adapter.jetty :as jetty]))

; Read the configuration file
(def config (read-string (slurp "config.clj")))


(defn -main []
  (let [conn (esr/connect (:url (:elastic config))
                          {:basic-auth [(:user (:elastic config))
                                        (:password (:elastic config))]})])
  (jetty/run-jetty app {:port (:port config)}))
