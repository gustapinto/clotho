(ns knit.core
  (:gen-class)
  (:require [ring.adapter.jetty :as jetty]
            [knit.lib.http.router :as router]
            [knit.lib.http.middleware :as middleware]
            [knit.features.services.http-handler :as services-http]
            [knit.lib.file.file :as file]
            [clj-yaml.core :as yaml]))

(defn routes
  [services]
  [[#"/knit/v1/services" (middleware/wrap-json (services-http/get-all-services services))]
   [#"/(.*)" (services-http/proxy-to-service services)]])

(defn -main
  [& _]
  (let [raw-config (file/read-as-str "config.yaml")
         config-map (yaml/parse-string raw-config)
         services (:services config-map)]
       (jetty/run-jetty
        (router/handle (routes services))
        {:port 9090})))
