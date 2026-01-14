(ns clotho.services.service
  (:require [clj-http.client :as client]
            [clojure.string :refer [split]]
            [clotho.datomic.service :as repo]
            [clotho.adapters.service :refer [datomic-entity->service service->datomic-entity]]))

(defn ->target-request
  [incoming-request target-url]
  (when incoming-request (merge incoming-request {:throw-exceptions false
                                                  :url target-url})))

(defn ->target-url
  [base-url uri]
  (cond (empty? base-url) nil
        (empty? uri) base-url
        :else (str base-url "/" uri)))

(defn call-service
  [{:keys [uri] :as request}
   {:keys [base-url]}]
  (->> (->target-url base-url uri)
       (->target-request request)
       client/request))

(defn parse-prefix
  [path]
  (cond (not (string? path)) ""
        (empty? path) ""
        :else (->> (split path #"/")
                   second
                   (str "/"))))

(defn find-service-for-path
  [db path]
  (->> path
       parse-prefix
       (repo/query-service-by-prefix db)
       datomic-entity->service))

(defn find-all-services
  [db]
  (->> db
       repo/query-all-services
       (map datomic-entity->service)))

(defn upsert-service
  [db service]
  (->> service
       service->datomic-entity
       (repo/upsert-service db)))