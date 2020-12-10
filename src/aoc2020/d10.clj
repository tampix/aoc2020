(ns aoc2020.d10
  (:require [aoc2020.util :as util]
            [clojure.string :as str]))

(defn parse
  []
  (let [ints (into (sorted-set) (util/read-input-ints 10))]
    (conj ints 0 (+ 3 (last ints)))))

(defn day10
  []
  (let [ints (parse)]
    ;; part1
    (let [freq (->> (partition 2 1 ints)
                    (map (fn [[a b]] (- b a)))
                    frequencies)]
      (println (* (freq 1) (freq 3))))
    ;; part2
    (let [ways (reduce #(assoc %1 %2 (->> (range (- %2 3) (inc %2))
                                          (keep %1)
                                          (reduce +)))
                       {(first ints) 1}
                       (rest ints))]
      (println (ways (last ints))))))
