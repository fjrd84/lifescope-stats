(defproject lifescope-stats "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"] [compojure "1.6.0"]]
  :main ^:skip-aot lifescope-stats.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all} :user {:aliases {"omni" ["do" ["clean"] ["with-profile" "production" "deps" ":tree"] ["ancient"] ["kibit"] ["cloverage"]]} :plugins [[jonase/eastwood "0.2.4"] [lein-cloverage "1.0.9"] [lein-ancient "0.6.10"] [lein-kibit "0.1.5"] [lein-bikeshed "0.4.1"]] }})
