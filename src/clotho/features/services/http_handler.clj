(ns clotho.features.services.http-handler
  (:require [clojure.string :as string]
            [clotho.features.services.use-cases :refer [call-service]]
            [clotho.lib.http.response :refer [not-found]]))

(defn get-all-services
  [services]
  (fn [_] {:status 200 :body services}))

(defn proxy-to-service
  [services]
  (fn [request] (let [path (:uri request)
                      service (some (fn [service]
                                        (when (string/starts-with? path (:prefix service)) service)) services)]
                  (cond (= service nil) (not-found request)
                        :else (call-service request service)))))
