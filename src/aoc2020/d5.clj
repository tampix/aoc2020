(ns aoc2020.d5
  (:require [aoc2020.util :as util]
            [clojure.string :as str]))

(defn seat->id
  [seat]
  (-> seat
      (str/replace #"." {"F" "0", "L" "0", "B" "1", "R" "1"})
      (Integer/parseInt 2)))

(defn day5
  []
  (let [[min-id max-id xor-id] (->> (util/read-input-lines 5)
                                    (map seat->id)
                                    (util/juxt-reduce [min max bit-xor]))]
    ;; part1
    (println max-id)
    ;; part2
    (println (reduce bit-xor xor-id (range min-id (inc max-id))))))

(comment
  (day5)

  )
