(ns clotho.unit.services.service-test
  (:require [clojure.test :refer [deftest testing are is]]
            [clotho.services.service :as s]))

(deftest parse-prefix-test
  (testing "test parse-prefix"
    (are [expected path] (= expected (s/parse-prefix path))
      "/v1" "/v1/some/path"
      "/v1" "/v1/foo"
      "/v1" "/v1"
      ""    ""
      ""    nil
      ""    '()
      ""    1)))

(deftest ->target-url-test
  (testing "test ->target-url"
    (are [expected base-url uri] (= expected (s/->target-url base-url uri))
      "http://example.com/api/users"    "http://example.com"     "api/users"
      "https://api.service.io/v1/data"  "https://api.service.io" "v1/data"
      "http://localhost:8080/health"    "http://localhost:8080"  "health"
      nil                               nil                      "api/users"
      nil                               nil                      nil
      nil                               ""                       "api/users"
      "http://example.com"              "http://example.com"     ""
      "http://example.com"              "http://example.com"     nil)))

(deftest ->target-request-test
  (testing "test ->target-request with valid incoming request"
    (is (= {:method :get
            :headers {"Content-Type" "application/json"}
            :throw-exceptions false
            :url "http://example.com/api/users"}
           (s/->target-request {:method :get
                                :headers {"Content-Type" "application/json"}}
                               "http://example.com/api/users"))))

  (testing "empty map incoming request should merge with target url"
    (is (= {:throw-exceptions false
            :url "http://example.com"}
           (s/->target-request {} "http://example.com"))))

  (testing "should override existing :throw-exceptions"
    (is (= {:throw-exceptions false
            :url "http://example.com"}
           (s/->target-request {:throw-exceptions true} "http://example.com"))))

  (testing "should override existing :url"
    (is (= {:url "http://new-url.com"
            :throw-exceptions false}
           (s/->target-request {:url "http://old-url.com"} "http://new-url.com"))))

  (testing "nil incoming request returns nil"
    (is (nil? (s/->target-request nil "http://example.com")))))
