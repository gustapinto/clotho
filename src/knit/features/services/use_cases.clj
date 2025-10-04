(ns knit.features.services.use-cases)

(defn get-all
  []
  (for [_ (range 1 11)
        :let [obj {:id (random-uuid) :data {:mock (format "mock-%s" (random-uuid))}}]] obj))
