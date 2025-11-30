(ns clotho.core
  (:gen-class)
  (:require [ring.adapter.jetty :as jetty]
            [clotho.lib.http.router :refer [handle]]
            [clotho.lib.http.middleware :refer [wrap-json]]
            [clotho.features.services.http-handler :refer [get-all-services proxy-to-service]]
            [clotho.features.services.repository :refer [setup-schema]]
            [datomic.api :as d]))

(defn routes
  [conn]
  [[#"/clotho/v1/services" (wrap-json (get-all-services conn))]
   [#"/(.*)" (proxy-to-service conn)]])

(defn -main
  [& _]
  (let [port (or (Integer/parseInt (System/getenv "CLOTHO_API_PORT")) 9090)
        db-uri (System/getenv "CLOTHO_DB_URI")
        _ (d/create-database db-uri)
        conn (d/connect db-uri)]
    (setup-schema conn)
    (jetty/run-jetty
     (handle (routes conn))
     {:port port})))
