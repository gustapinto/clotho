(ns clotho.unit.adapters.service-test
  (:require [clojure.test :refer [deftest testing is]]
            [clotho.adapters.service :refer [datomic-entity->service service->datomic-entity]]))

(deftest datomic-entity->service-test
  (testing "test datomic-entity->service with a valid map"
    (let [input #:service{:name "foo"
                          :prefix "/bar"
                          :base-url "https://foo/bar"}
          expected {:name "foo"
                    :prefix "/bar"
                    :base-url "https://foo/bar"}
          actual (datomic-entity->service input)]
      (is (= expected actual))))

  (testing "test datomic-entity->service with a nil map"
    (let [actual (datomic-entity->service nil)]
      (is (nil? actual)))))

(deftest service->datomic-entity-test
  (testing "test service->datomic-entity with a valid map"
    (let [input {:name "foo"
                 :prefix "/bar"
                 :base-url "https://foo/bar"}
          expected #:service{:name "foo"
                             :prefix "/bar"
                             :base-url "https://foo/bar"}
          actual (service->datomic-entity input)]
      (is (= expected actual))))

  (testing "test service->datomic-entity with a nil map"
    (let [actual (service->datomic-entity nil)]
      (is (nil? actual))))

  (testing "test service->datomic-entity with a map with no name"
    (let [input {:prefix "/bar"
                 :base-url "https://foo/bar"}
          expected #:service{:name nil
                             :prefix "/bar"
                             :base-url "https://foo/bar"}
          actual (service->datomic-entity input)]
      (is (= expected actual)))))
