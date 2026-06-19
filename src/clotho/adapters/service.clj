(ns clotho.adapters.service)

(defn datomic-entity->service
  [{:keys [:service/name
           :service/prefix
           :service/base-url]
    :as entity}]
  (when entity {:name name
                :prefix prefix
                :base-url base-url}))

(defn service->datomic-entity
  [{:keys [name
           prefix
           base-url]
    :as service}]
  (when service #:service{:name name
                          :prefix prefix
                          :base-url base-url}))

(defn request-body->service
  [{:strs [name
           prefix
           base-url]
    :as request-body}]
  (when request-body
    {:name name
     :prefix prefix
     :base-url base-url}))