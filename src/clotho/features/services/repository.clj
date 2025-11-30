(ns clotho.features.services.repository
  (:require [datomic.api :as d]))

(def service-schema [{:db/ident :service/name
                      :db/valueType :db.type/string
                      :db/cardinality :db.cardinality/one}

                     {:db/ident :service/prefix
                      :db/valueType :db.type/string
                      :db/cardinality :db.cardinality/one}

                     {:db/ident :service/base-url
                      :db/valueType :db.type/string
                      :db/cardinality :db.cardinality/one}])

(defn setup-schema
  [conn]
  (d/transact conn service-schema))

(defn find-service-by-prefix
  [conn prefix]
  (let [db (d/db conn)
        query '[:find (pull ?e [:service/name
                                :service/prefix
                                :service/base-url])
                :in $ ?prefix
                :where [?e :service/prefix ?prefix]]
        results (d/q query db prefix)]
    (ffirst results)))

(defn find-all-services
  [conn]
  (let [db (d/db conn)
        query '[:find (pull ?e [:service/name
                                :service/prefix
                                :service/base-url])
                :where [?e :service/name]]
        results (d/q query db)]
    (flatten results)))
