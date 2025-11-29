(ns clotho.features.services.use-cases
  (:require [clj-http.client :as client]
            [clojure.string :refer [starts-with?]]))

(defn call-service
  [request service]
  (let [request-uri (:uri request)
        base-url (:base-url service)
        target-url (format "%s/%s" base-url request-uri)
        service-request (merge request {:throw-exceptions false
                                        :url target-url})]
  (client/request service-request)))

(defn path-targets-service?
  [path service]
  (starts-with? path (:prefix service)))

(defn find-service-for-path
  "return a service map if path is target of some service, else nil"
  [path services]
  (some (fn [service] (when (path-targets-service? path service) service))
        services))
