(ns clotho.http.handlers.service
  (:require [clotho.services.service :refer [call-service find-service-for-path find-all-services]]
            [clotho.http.response :refer [not-found]]
            [clotho.http.middleware :refer [wrap-json]]))

(defn get-all-services
  [{:keys [db]}]
  (fn [_] {:status 200
           :body (find-all-services db)}))

(defn proxy-to-service
  [{:keys [db]}]
  (fn [{:keys [uri] :as request}]
    (let [service (find-service-for-path db uri)]
      (if (nil? service)
        ((wrap-json not-found) request)
        (call-service request service)))))
