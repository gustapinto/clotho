(ns clotho.http.router
  (:require [clotho.http.middleware :refer [wrap-error wrap-json wrap-log]]
            [clotho.http.response :refer [not-found]]))

(defn- ^:private route-matches?
  [request-uri
   request-method
   route-path-expr
   route-method]
  (and (boolean (re-matches route-path-expr request-uri))
       (or (= route-method :any)
           (= route-method request-method))))

(defn handle
  [routes]
  (fn [{:keys [uri request-method] :as request}]
    (let [route-handler (some (fn [[m p h]] (when (route-matches? uri request-method p m) h)) routes)
          handler (wrap-log (wrap-error (or route-handler (wrap-json not-found))))]
      (handler request))))
