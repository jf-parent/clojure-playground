(ns collatz-conjecture)

(defn collatz-walk [num steps]
  (cond
    (= num 1) steps
    (= 0 (mod num 2)) (collatz-walk (/ num 2) (inc steps))
    :else (collatz-walk (inc (* 3 num)) (inc steps))))

(defn collatz [num]
  (when (< num 0)
    (throw (Exception. "negative value is an error")))
  (when (= num 0)
    (throw (Exception. "zero is an error")))
  (collatz-walk num 0))
