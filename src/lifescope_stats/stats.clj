(ns lifescope-stats.stats
  (:use [compojure.core])
  (:require [clojurewerkz.elastisch.rest :as esr]
            [clojurewerkz.elastisch.rest.document :as esd]
            [clojurewerkz.elastisch.query :as q]
            [clojurewerkz.elastisch.aggregation :as a]
            [clojurewerkz.elastisch.rest.response :as esrsp]
            [clojure.pprint :as pp]))

;; Read the configuration file
(def config (read-string (slurp "config.clj")))

;; Set up the connection to the elasticsearch
(def conn (esr/connect (:url (:elastic config))
                       {:basic-auth [(:user (:elastic config))
                                     (:password (:elastic config))]}))

;; Get the total count of analyzed messages in the system
(defn total-count []
  (let [res (esd/search conn
                        "analysis"
                        ""
                        {
                         :query (q/term :_type "health")
                         :size 0
                         }
                        )
        n (esrsp/total-hits res) ] 
    n))

;; Aggregate all the solutions for a specific problem
(defn find-solutions-to-problem [problem]
  (let [res (esd/search conn
                        "analysis"
                        ""
                        {
                         :query (q/wildcard :analysis.problem (str problem "*"))
                         :size 0
                         :aggregations {
                                        :solutions {
                                                    :terms {
                                                            :field "analysis.solution"
                                                            :size 1000
                                                            }
                                                    }
                                        }
                         }
                        )]
    res))

;; Aggregate all the problems identified ever by the system
(defn find-all-problems []
  (let [res (esd/search conn
                        "analysis"
                        ""
                        {
                         :size 0
                         :aggregations {
                                        :solutions {
                                                    :terms {
                                                            :field "analysis.problem"
                                                            :size 50000
                                                            }
                                                    }
                                        }
                         }
                        )]
    res))

;; Find messages for a given problem and solution
(defn find-problem-solution-matches [problem solution]
  (let [res (esd/search conn
                        "analysis"
                        ""
                        {
                         :query {
                                 :bool {
                                        :must [
                                               {
                                                :match {
                                                        :analysis.problem problem
                                                        }
                                                }
                                               {
                                                :match {
                                                        :analysis.solution solution
                                                        }
                                                }
                                               ]
                                        }

                                 }
                         }
                        :size 10
                        )

        hits (esrsp/hits-from res)
        ]
    hits))


;; Perform a wildcard search within the queries, limited to 200 elements
(defn wildcard-search [search-word]
  (let [res (esd/search conn
                        "analysis"
                        ""
                        { 
                         :query (q/wildcard :query (str search-word "*"))  
                         :size 200
                         }
                        )

        hits (esrsp/hits-from res)
        n (esrsp/total-hits res)]
    {:results hits :count n}))

;; Perform a generic search, limited to 200 elements
(defn match-search [search-word]
  (let [res (esd/search conn
                        "analysis"
                        ""
                        {
                         :query
                         {
                          :match {:_all search-word}
                          }
                         :size 200
                         })
        hits (esrsp/hits-from res)
        n (esrsp/total-hits res)]
    {:results hits :count n}))

;; It returns a sample of 1000 elements of the elasticsearch
(defn get-1000 []
  (let [res (esd/search conn
                        "analysis"
                        ""
                        {
                         :query (q/term :source "twitter")
                         :size 1000
                         }
                        )
        hits (esrsp/hits-from res)
        ] 
    hits))

;; It counts how many elements have been analyzed for a given query
(defn query-counter [query]
  (let [res (esd/search conn
                        "analysis"
                        ""
                        {
                         :query (q/term :query query)
                         }
                        )
        n (esrsp/total-hits res)]
    n))

;; It obtains a list with all the unique values for a given key
(defn unique-values [key]
  (let [hits (get-1000)]
    (distinct
     (map key 
          (map :_source hits)
          ))))

;; It obtains a list with all the unique analysis values for a given key
(defn unique-analysis-values [key]
  (let [hits (get-1000)]
    (distinct
     (map key 
          (map :analysis
               (map :_source hits))))))


;; It obtains a list with all the unique queries currently present in the system
(defn unique-queries []
  (unique-values :query))

;; It returns a hash map with the current analysis count for each query
(defn all-queries-count []
  (let [queries (unique-queries)
        counted (map #(hash-map 
                       :query %, 
                       :count (query-counter %)) 
                     queries)]
    counted
    ))

