(ns clotho.core
  (:gen-class)
  (:require [ring.adapter.jetty :as jetty]
            [clotho.lib.http.router :refer [handle]]
            [clotho.lib.http.middleware :refer [wrap-json]]
            [clotho.lib.datomic.datomic :refer [connect]]
            [clotho.features.services.http-handler :refer [get-all-services proxy-to-service]]
            [clotho.features.services.datomic :refer [service-schema, insert-sample-service]]))

(defn -main
  [& _]
  (let [port (or (Integer/parseInt (System/getenv "CLOTHO_API_PORT")) 9090)
        db-uri (System/getenv "CLOTHO_DB_URI")
        conn (connect db-uri service-schema)
        routes [[#"/clotho/v1/services" (wrap-json (get-all-services conn))]
                [#"/(.*)" (proxy-to-service conn)]]]
    (insert-sample-service conn)
    (jetty/run-jetty
     (handle routes)
     {:port port})))
