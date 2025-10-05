(ns knit.lib.http.router
  (:require [knit.lib.http.middleware :as middleware]
            [knit.lib.http.response :as response]))

(defn handle
  [routes]
  (fn [request]
    (let [path (:uri request)
          handler (some (fn [[p h]]
                          (when (not= (re-matches p path) nil) h)) routes)]
      ((middleware/wrap-log
         (or handler (middleware/wrap-json response/not-found)))
       request))))