(ns clotho.http.response)

(defn ^:private error
  ([code retriable]
   {:error code :retriable retriable})

  ([code retriable details]
   {:error code :retriable retriable :details details}))

(defn not-found
  [{:keys [uri]}]
  {:status 404
   :body (error "route-not-found" false {:message "route not found" :route uri})})

(defn internal-error
  [e]
  {:status 500
   :body (error "internal-error" true {:message (.getMessage e)})})