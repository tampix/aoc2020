(ns aoc2020.d9
  (:require [aoc2020.util :as util]))

(defn part1
  [window-size nums]
  (->> (partition (inc window-size) 1 nums)
       (some (fn [window]
               (let [haystack (butlast window)
                     needle (last window)]
                 (when-not (util/find-sum haystack needle)
                   needle))))))

(defn day9
  []
  (let [ints   (util/read-input-ints 9)
        needle (part1 25 ints)]
    ;; part1
    (println needle)
    ;; part2
    (->> (for [window-size (range 2 (dec (count ints)))
               window      (partition window-size 1 ints)
               :when       (= (reduce + window) needle)]
           (reduce + (util/juxt-reduce [min max] window)))
         first
         println)))
