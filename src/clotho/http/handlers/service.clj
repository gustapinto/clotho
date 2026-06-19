(ns clotho.http.handlers.service
  (:require [clotho.services.service :refer [call-service find-service-for-path find-all-services upsert-service]]
            [clotho.http.response :refer [not-found]]
            [clotho.http.middleware :refer [wrap-json]]
            [clotho.adapters.service :refer [request-body->service]]
            [clojure.data.json :refer [read-str]]))

(defn get-all-services-handler
  [{:keys [db]}]
  (fn [_]
    {:status 200
     :body (find-all-services db)}))

(defn proxy-to-service-handler
  [{:keys [db]}]
  (fn [{:keys [uri] :as request}]
    (let [service (find-service-for-path db uri)]
      (if (nil? service)
        ((wrap-json not-found) request)
        (call-service request service)))))

(defn upsert-service-handler
  [{:keys [db]}]
  (fn [{:keys [body]}]
    (let [service (some->> body
                           slurp
                           read-str
                           request-body->service)]
      (upsert-service db service)
      {:status 204})))
