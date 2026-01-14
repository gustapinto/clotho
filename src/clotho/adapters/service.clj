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