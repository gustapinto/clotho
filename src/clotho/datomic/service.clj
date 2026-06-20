(ns clotho.datomic.service
  (:require [datomic.api :as d]))

(def service-schema [{:db/ident :service/name
                      :db/valueType :db.type/string
                      :db/cardinality :db.cardinality/one
                      :db/unique :db.unique/identity}

                     {:db/ident :service/prefix
                      :db/valueType :db.type/string
                      :db/cardinality :db.cardinality/one}

                     {:db/ident :service/base-url
                      :db/valueType :db.type/string
                      :db/cardinality :db.cardinality/one}])

(defn query-service-by-prefix
  [conn prefix]
  (let [db (d/db conn)
        query '[:find (pull ?e [:service/name
                                :service/prefix
                                :service/base-url])
                :in $ ?prefix
                :where [?e :service/prefix ?prefix]]
        results (try
                  (d/q query db prefix)
                  (catch Exception _ []))]
    (ffirst results)))

(defn query-all-services
  [conn]
  (let [db (d/db conn)
        query '[:find (pull ?e [:service/name
                                :service/prefix
                                :service/base-url])
                :where [?e :service/name]]
        results (try
                  (d/q query db)
                  (catch Exception _))]
    (flatten results)))

(defn upsert-service
  [db service]
  @(d/transact db [service]))
