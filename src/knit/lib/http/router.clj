(ns knit.lib.http.router
  (:require [knit.lib.http.middleware :as middleware]
            [knit.lib.http.response :as response]))

(defn handle
  [routes]
  (fn [request]
    (let [method (:request-method request)
          path (:uri request)
          handler (some (fn [[m p h]] (when (and (= m method) (= p path)) h)) routes)]
      ((middleware/wrap-log (middleware/wrap-json (or handler response/not-found)))
       request))))