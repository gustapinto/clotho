(ns knit.features.services.http-handler
  (:require [knit.features.services.use-cases :as service-use-cases]))

(defn get-all
  [_]
  (let [all-services (service-use-cases/get-all)]
    {:status 200
     :body all-services}))

(defn upsert
  [_]
  {:status 204})

(defn redirect
  [_]
  {:status 302})