(ns clotho.http.router
  (:require [clotho.http.middleware :refer [wrap-error wrap-json wrap-log]]
            [clotho.http.response :refer [not-found]]))

(defn handle
  [routes]
  (fn [{:keys [uri] :as request}]
    (let [handler (some (fn [[p h]]
                          (when (not= (re-matches p uri) nil) h)) routes)]
      ((wrap-log
        (wrap-error
         (or handler (wrap-json not-found))))
       request))))