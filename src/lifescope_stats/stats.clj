(ns lifescope-stats.stats
  (:use [compojure.core])
  (:require [clojurewerkz.elastisch.rest :as esr]
            [clojurewerkz.elastisch.rest.document :as esd]
            [clojurewerkz.elastisch.query :as q]
            [clojurewerkz.elastisch.aggregation :as a]
            [clojurewerkz.elastisch.rest.response :as esrsp]
            [clojure.pprint :as pp]))

; Read the configuration file
(def config (read-string (slurp "config.clj")))

(def conn (esr/connect (:url (:elastic config))
                       {:basic-auth [(:user (:elastic config))
                                     (:password (:elastic config))]}))


(defn wildcard-search [search-word]
  (let [res (esd/search conn
                        "analysis"
                        ""
                        :query
                        (q/wildcard :query (str search-word "*")))
        hits (esrsp/hits-from res)
        n (esrsp/total-hits res)]
    {:results hits :count n}))

(defn match-search [search-word]
  (let [res (esd/search conn
                        "analysis"
                        ""
                        {
                         :query
                         {
                          :match {:_all search-word}
                          }
                         })
        hits (esrsp/hits-from res)
        n (esrsp/total-hits res)]
    {:results hits :count n}))

 
