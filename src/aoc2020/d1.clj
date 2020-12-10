(ns aoc2020.d1
  (:require [aoc2020.util :as util]))

(defn day1
  []
  (let [nums (set (util/read-input-ints 1))]
    ;; part1
    (println (some->> (util/find-sum nums 2020)
                      (apply *)))
    ;; part2
    (println (some #(some->> (util/find-sum nums (- 2020 %))
                             (apply * %))
                   nums))))
