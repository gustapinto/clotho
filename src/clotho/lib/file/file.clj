(ns clotho.lib.file.file
  (:require [clojure.java.io :refer [input-stream]]))

(defn read-as-str
  [filepath]
  (with-open [r (input-stream filepath)]
    (slurp r)))