(ns play-test-check.core)

(require '[clojure.test.check :as tc])
(require '[clojure.test.check.generators :as gen])
(require '[clojure.test.check.properties :as prop #?@(:cljs [:include-macros true])])

;; Int test
(defn over-10
  "Is x over 10?"
  [x]
  (if (> x 10)
    true
    false))

(def test-over-10
  (prop/for-all [v gen/int]
                (if (> v 10)
                  (= (over-10 v) true)
                  (= (over-10 v) false))))

(tc/quick-check 100 test-over-10)

;; Vector test
(defn sum [array]
  (reduce + array))

(def test-sum-array
  (prop/for-all [v (gen/vector gen/int)]
                (= (reduce + v) (sum v))))

(tc/quick-check 100 test-sum-array)


;; Keyword test
(defn is-good [meal]
  (case meal
    :pizza :no-good
    :pasta :no-good
    :rice :very-good
    :salad :very-good
    :hungry))

(def test-is-good
  (prop/for-all [v (gen/elements [:pizza :pasta :rice :salad nil])]
                (case (is-good v)
                  :no-good (or (= v :pizza) (= v :pasta))
                  :very-good (or (= v :rice) (= v :salad))
                  :hungry (= v nil))))

(tc/quick-check 100 test-is-good)
