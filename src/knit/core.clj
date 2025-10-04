(ns knit.core
  (:gen-class)
  (:require [ring.adapter.jetty :as jetty]
            [knit.lib.http.router :as router]
            [knit.features.services.http-handler :as services-http]))

(def routes [[:get "/v1/services" services-http/get-all]
             [:post "/v1/services" services-http/upsert]
             [:any "/" services-http/redirect]])

(defn -main
  [& _]
  (jetty/run-jetty (router/handle routes) {:port 9090}))
