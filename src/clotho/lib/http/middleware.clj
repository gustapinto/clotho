(ns clotho.lib.http.middleware
  (:require [clojure.tools.logging :as logging]
            [clojure.data.json :as json]))

(defn wrap-log
  [handler]
  (fn [request]
    (let [method (:request-method request)
          path (:uri request)
          res (handler request)
          status-code (:status res)]
      (logging/infof "%s %s - %s" method path status-code)
      res)))

(defn wrap-json
  [handler]
  (fn [request]
    (let [res (handler request)
          status (:status res)
          body (:body res)
          headers (:headers res)]
      {:status status
       :headers (merge {"Content-Type", "application/json"} headers)
       :body (cond (or (= status 204) (= body nil)) nil
                   :else (json/write-str body))})))
