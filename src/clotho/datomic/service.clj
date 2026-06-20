(ns clotho.datomic.service
  (:require [datomic.api :as d]
            [clojure.core.memoize :as memoize]))

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

(defn ^{:clojure.core.memoize/args-fn rest} query-service-by-prefix
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

(def cached-query-service-by-prefix (memoize/memo query-service-by-prefix))

(defn ^{:clojure.core.memoize/args-fn rest} query-all-services
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

(def cached-query-all-services (memoize/memo query-all-services))

(defn upsert-service
  [conn service]
  @(d/transact conn [service])
  (println (:service/prefix service))
  (memoize/memo-clear! cached-query-service-by-prefix [conn (:service/prefix service)])
  (memoize/memo-clear! cached-query-all-services [conn]))
