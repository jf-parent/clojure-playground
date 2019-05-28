(ns meetup
  (:require [clojure.string :as str] [clj-time.core :as t]))

(def DAY-OF-WEEK-MAP {:monday 1 :tuesday 2 :wednesday 3 :thursday 4 :friday 5 :saturday 6 :sunday 7})

(defn find-date [upper-dow lower-dow start-day direction]
  (direction start-day (mod (- upper-dow lower-dow) 7)))

(defn get-start-day [month year condition]
  (case condition
   :first 1
   :second 8
   :third 15
   :fourth 22
   :teenth 13
   :last (.getMaximumValue (.dayOfMonth (t/date-time year month 1)))))

(defn meetup [month year day condition]
  (let [target-dow (DAY-OF-WEEK-MAP day)
        start-day (get-start-day month year condition)
        start-date (t/date-time year month start-day)
        start-dow (.getDayOfWeek start-date)]
    (if (= condition :last)
      [year month (find-date start-dow target-dow start-day -)]
      [year month (find-date target-dow start-dow start-day +)])))
