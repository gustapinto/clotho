(ns clotho.lib.file.file
  (:require [clojure.java.io :as io]))

(defn read-as-str
  [filepath]
  (with-open [r (io/input-stream filepath)]
    (slurp r)))