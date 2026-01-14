(ns clotho.config.context
  (:require [clotho.datomic.lib :refer [connect]]
            [clotho.datomic.service :refer [service-schema]]))

(defn ^:private getenv-port
  []
  (or (Integer/parseInt (System/getenv "CLOTHO_API_PORT")) 9090))

(defn ^:private getenv-db-uri
  []
  (or (System/getenv "CLOTHO_DB_URI") ""))

(defn make-ctx
  []
  {:db (connect (getenv-db-uri) service-schema)
   :http {:port (getenv-port)}})