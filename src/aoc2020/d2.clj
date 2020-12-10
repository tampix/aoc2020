(ns aoc2020.d2
  [:require [aoc2020.util :as util]])

(defn xor
  [a b]
  (and (or a b)
       (not (and a b))))

(defn input->rules
  [lines]
  (for [line lines
        :let [[_ lo hi [c] password] (re-matches #"(\d+)-(\d+) (\w): (\w+)" line)]]
    [(Integer/parseInt lo) (Integer/parseInt hi) (mapv #{c} password)]))

(defn part1-valid?
  [[lo hi matches]]
  (<= lo (count (keep identity matches)) hi))

(defn part2-valid?
  [[lo hi matches]]
  (xor (matches (dec lo)) (matches (dec hi))))

(defn day2
  []
  (let [rules (input->rules (util/read-input-lines 2))]
    (doseq [part-pred [part1-valid? part2-valid?]]
      (println (count (filter part-pred rules))))))
