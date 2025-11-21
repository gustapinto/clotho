(ns clotho.lib.http.response)

(defn- error
  ([code retriable]
   {:error code :retriable retriable})

  ([code retriable details]
   {:error code :retriable retriable :details details}))

(defn not-found
  [request]
  {:status 404
   :body (error "route-not-found" false {:message "route not found" :route (:uri request)})})

(defn internal-error
  [e]
  {:status 500
   :body (error "internal-error" true {:message (.getMessage e)})})