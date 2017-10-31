(defproject lifescope-stats "0.1.0-SNAPSHOT"

  :description "FIXME: write description"

  :url "http://example.com/FIXME"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :plugins [[lein-ring "0.8.10"] [lein-auto "0.1.3"] [lein-cljfmt "0.5.7"]]
  
  ; See https://github.com/weavejester/lein-ring#web-server-options for the
  ; various options available for the lein-ring plugin
  :ring {:handler restful-clojure.handler/app
         :nrepl {:start? true
                 :port 9998}}

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring/ring-core "1.2.1"]
                 [javax.servlet/servlet-api "2.5"]
                 [ring-mock "0.1.5"]
                 [ring/ring-json "0.2.0"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 [clojurewerkz/elastisch "2.2.0"]
                 [compojure "1.6.0"]]

  :main ^:skip-aot lifescope-stats.core

  :target-path "target/%s"

  :profiles {:uberjar {:aot :all}
             :user {
                    :aliases
                    {"omni"
                     ["do"
                      ["clean"]
                      ["with-profile" "production" "deps" ":tree"]
                      ["ancient"] ["kibit"] ["cloverage"]]
                     }
                    :plugins
                    [
                     [jonase/eastwood "0.2.4"]
                     [lein-cloverage "1.0.9"]
                     [lein-ancient "0.6.10"]
                     [lein-kibit "0.1.5"]
                     [cider/cider-nrepl "0.9.0"]
                     [lein-bikeshed "0.4.1"]] }
             }
  )
