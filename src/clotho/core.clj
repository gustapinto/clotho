(ns clotho.core
  (:gen-class)
  (:require [ring.adapter.jetty :as jetty]
            [clotho.lib.http.router :refer [handle]]
            [clotho.lib.http.middleware :refer [wrap-json]]
            [clotho.features.services.http-handler :refer [get-all-services proxy-to-service]]
            [clotho.lib.file.file :refer [read-as-str]]
            [datomic.api :as d]
            [clj-yaml.core :refer [parse-string]]))

(defn routes
  [services]
  [[#"/clotho/v1/services" (wrap-json (get-all-services services))]
   [#"/(.*)" (proxy-to-service services)]])

(defn -main
  [& _]
  (let [raw-config (read-as-str "config.yaml")
        config (parse-string raw-config)
        services (or (:services config) [])
        port (or (Integer/parseInt (:port config)) 9090)
        conn (d/connect (:db-uri config))]
    (jetty/run-jetty
     (handle (routes services))
     {:port port})))
