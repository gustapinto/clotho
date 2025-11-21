(ns clotho.features.services.http-handler
  (:require [clotho.features.services.use-cases :refer [call-service find-service-for-path]]
            [clotho.lib.http.response :refer [not-found]]
            [clotho.lib.http.middleware :refer [wrap-json]]))

(defn get-all-services
  [services]
  (fn [_] {:status 200
           :body services}))

(defn proxy-to-service
  [services]
  (fn [request]
    (let [path (:uri request)
          service (find-service-for-path path services)]
      (if (nil? service)
        ((wrap-json not-found) request)
        (call-service request service)))))
