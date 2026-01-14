(ns clotho.http.middleware
  (:require [clojure.tools.logging :refer [infof]]
            [clojure.data.json :refer [write-str]]
            [clotho.http.response :refer [internal-error]]))

(defn wrap-log
  [handler]
  (fn [{:keys [request-method uri] :as request}]
    (let [{:keys [status] :as res} (handler request)]
      (infof "%s %s - %s" request-method uri status)
      res)))

(defn wrap-json
  [handler]
  (fn [request]
    (let [{:keys [status body headers]} (handler request)]
      {:status status
       :headers (merge {"Content-Type", "application/json"} headers)
       :body (cond (or (= status 204) (= body nil)) nil
                   :else (write-str body :escape-slash false))})))

(defn wrap-error
  [handler]
  (fn [request]
    (try (handler request)
         (catch Exception e (internal-error e)))))