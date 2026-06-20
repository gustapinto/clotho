(ns clotho.core
  (:gen-class)
  (:require [ring.adapter.jetty :as jetty]
            [clotho.http.router :refer [handle]]
            [clotho.http.middleware :refer [wrap-json]]
            [clotho.http.handlers.service :refer [get-all-services-handler proxy-to-service-handler upsert-service-handler]]
            [clotho.config.context :refer [make-ctx]]))

(defn ^:private routes
  [ctx]
  [[:get #"/clotho/v1/services" (wrap-json (get-all-services-handler ctx))]
   [:post #"/clotho/v1/services" (wrap-json (upsert-service-handler ctx))]
   [:any #"/(.*)" (proxy-to-service-handler ctx)]])

(defn -main
  [& _]
  (let [ctx (make-ctx)]
    (jetty/run-jetty
     (handle (routes ctx))
     {:port (-> ctx :http :port)})))
