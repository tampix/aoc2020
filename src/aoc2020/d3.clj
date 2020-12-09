(ns aoc2020.d3
  (:require [aoc2020.util :as util]))

(defn count-trees
  [lines [right down]]
  (->> lines
       (keep-indexed #(let [idx (/ %1 down)]
                        (when (int? idx)
                          (get %2 (mod (* idx right)
                                       (count %2))))))
       (filter #{\#})
       count))

(defn day3
  []
  (let [lines  (util/read-input-lines 3)
        counts (into {}
                     (map (juxt identity (partial count-trees lines)))
                     [[1 1] [3 1] [5 1] [7 1] [1 2]])]
    (println (counts [3 1]))
    (println (reduce * (vals counts)))))

(comment
  (day3)

  )
