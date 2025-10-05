(defproject knit "0.1.0"
  :dependencies [[org.clojure/clojure "1.12.2"]
                 [org.clojure/data.json "2.5.1"]
                 [org.clojure/tools.logging "1.3.0"]
                 [ring/ring-core "1.15.3"]
                 [ring/ring-jetty-adapter "1.15.3"]
                 [org.slf4j/slf4j-simple "2.0.16"]
                 [clj-commons/clj-yaml "1.0.29"]
                 [clj-http "3.13.1"]]
  :main ^:skip-aot knit.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]
                       :uberjar-name "knit.jar"}})
