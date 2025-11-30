(ns clotho.features.services.http-handler
  (:require [clotho.features.services.use-cases :refer [call-service find-service-for-path, find-all-services]]
            [clotho.lib.http.response :refer [not-found]]
            [clotho.lib.http.middleware :refer [wrap-json]]))

(defn get-all-services
  [conn]
  (fn [_] {:status 200
           :body (find-all-services conn)}))

(defn proxy-to-service
  [conn]
  (fn [request]
    (let [path (:uri request)
          service (find-service-for-path path conn)]
      (if (nil? service)
        ((wrap-json not-found) request)
        (call-service request service)))))
