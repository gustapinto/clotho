(ns clotho.core
  (:gen-class)
  (:require [ring.adapter.jetty :as jetty]
            [clotho.http.router :refer [handle]]
            [clotho.http.middleware :refer [wrap-json]]
            [clotho.http.handlers.service :refer [get-all-services proxy-to-service]]
            [clotho.datomic.service :refer [upsert-sample-service]]
            [clotho.config.context :refer [make-ctx]]))

(defn ^:private routes
  [ctx]
  [[#"/clotho/v1/services" (wrap-json (get-all-services ctx))]
   [#"/(.*)" (proxy-to-service ctx)]])

(defn -main
  [& _]
  (let [ctx (make-ctx)]
    (upsert-sample-service (:db ctx))
    (jetty/run-jetty
     (handle (routes ctx))
     {:port (-> ctx :http :port)})))
