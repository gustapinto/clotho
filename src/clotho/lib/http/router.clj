(ns clotho.lib.http.router
  (:require [clotho.lib.http.middleware :refer [wrap-error wrap-json wrap-log]]
            [clotho.lib.http.response :refer [not-found]]))

(defn handle
  [routes]
  (fn [request]
    (let [path (:uri request)
          handler (some (fn [[p h]]
                          (when (not= (re-matches p path) nil) h)) routes)]
      ((wrap-log
        (wrap-error
         (or handler (wrap-json not-found))))
       request))))