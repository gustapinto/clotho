(ns clotho.features.services.use-cases
  (:require [clj-http.client :as client]
            [clojure.string :refer [split]]
            [clotho.features.services.repository :as repo]))

(defn call-service
  [request service]
  (let [request-uri (:uri request)
        base-url (:base-url service)
        target-url (format "%s/%s" base-url request-uri)
        service-request (merge request {:throw-exceptions false
                                        :url target-url})
        response (client/request service-request)]
    response))

(defn find-service-for-path
  "return a service map if path is target of some service, else nil"
  [path conn]
  (let [path-parts (split path #"/")
        prefix (get path-parts 1)
        service (repo/find-service-by-prefix conn (str "/" prefix))]
    {:name (:service/name service)
     :prefix (:service/prefix service)
     :base-url (:service/base-url service)}))

(defn find-all-services
  [conn]
  (let [services (repo/find-all-services conn)]
  services))
