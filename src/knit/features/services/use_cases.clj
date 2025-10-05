(ns knit.features.services.use-cases
  (:require [clj-http.client :as client]))

(defn call-service
  [request service]
  (let [request-uri (:uri request)
        base-url (:base-url service)
        target-url (format "%s/%s" base-url request-uri)]
  (client/request (merge request {:throw-exceptions false
                                  :url target-url}))))