(ns aoc2020.d6
  (:require [aoc2020.util :as util]
            [clojure.set :as set]
            [clojure.string :as str]))

(defn day6
  []
  (let [input   (util/read-input 6)
        answers (->> (str/split input #"\n\n")
                     (map str/split-lines))]
    (doseq [part-fn [set/union set/intersection]]
      (->> answers
           (map #(->> (map set %)
                      (reduce part-fn)
                      count))
           (reduce +)
           println))))
