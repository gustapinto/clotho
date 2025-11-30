(ns clotho.lib.datomic.datomic
  (:require [datomic.api :as d]))

(defn connect
  [db-uri schema]
  (d/create-database db-uri)
  (let [conn (d/connect db-uri)]
    (d/transact conn schema)
    conn))
