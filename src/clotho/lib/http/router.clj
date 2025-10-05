(ns clotho.lib.http.router
  (:require [clotho.lib.http.middleware :as middleware]
            [clotho.lib.http.response :as response]))

(defn handle
  [routes]
  (fn [request]
    (let [path (:uri request)
          handler (some (fn [[p h]]
                          (when (not= (re-matches p path) nil) h)) routes)]
      ((middleware/wrap-log
         (or handler (middleware/wrap-json response/not-found)))
       request))))