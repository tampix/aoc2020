(ns aoc2020.d8
  (:require [aoc2020.util :as util]
            [clojure.string :as str]))

(defn run
  [stack]
  (loop [ip      0
         acc     0
         visited #{}]
    (if-let [[op arg] (get stack ip)] 
      (cond
        (visited ip) {:acc acc :ip ip :err {:code :infinite-loop :ip ip}}
        (= op "acc") (recur (inc ip) (+ acc arg) (conj visited ip))
        (= op "jmp") (recur (+ ip arg) acc (conj visited ip))
        (= op "nop") (recur (inc ip) acc (conj visited ip)))
      {:acc acc :ip ip})))

(defn day8
  []
  (let [stack (mapv #(let [[op arg] (str/split % #" ")]
                       [op (Integer/parseInt arg)])
                    (util/read-input-lines 8))]
    ;; part1
    (println (:acc (run stack)))
    ;; part2
    (->> (range (count stack))
         (filter (comp #{"jmp" "nop"} first stack))
         (map #(update-in stack [% 0] {"jmp" "nop", "nop" "jmp"}))
         (map run)
         (remove :err)
         (some :acc)
         println)))

(comment
  (day8)

  )
