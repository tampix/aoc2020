(ns aoc2020.d11
  (:require [aoc2020.util :as util]))

(def adjacent-directions
  (for [i (range -1 2)
        j (range -1 2)
        :when (not= i j 0)]
    [i j]))

(defn occupied-neighbor-seat?
  [seats pos dir]
  (->> (map + pos dir)
       (get-in seats)
       (= \#)))

(defn occupied-visible-seat?
  [seats pos dir]
  (let [adj-pos (map + pos dir)
        adj-seat (get-in seats adj-pos)]
    (if (= adj-seat \.)
      (recur seats adj-pos dir)
      (= adj-seat \#))))

(defn update-seat
  [seats pos visible?]
  (let [seat      (get-in seats pos)
        tolerance (if visible? 5 4)
        pred-fn   (if visible? occupied-visible-seat? occupied-neighbor-seat?)
        n         (delay (count (filter (partial pred-fn seats pos)
                                        adjacent-directions)))]
    (cond
      (and (= seat \L) (= @n 0))          \#
      (and (= seat \#) (>= @n tolerance)) \L
      :else                               seat)))

(defn update-seats
  [seats visible?]
  (let [new-seats (->> (for [i (range (count (seats 0)))
                             j (range (count seats))
                             :let [pos [j i]
                                   seat (get-in seats pos)
                                   new-seat (update-seat seats pos visible?)]
                             :when (not= seat new-seat)]
                         [pos new-seat])
                       (reduce (partial apply assoc-in) seats))]
    (if (= seats new-seats)
      seats
      (recur new-seats visible?))))

(defn count-occupied-seats
  [seats]
  (count (mapcat (partial keep #{\#}) seats)))

;; runs in 13 seconds...
(defn day11
  []
  (let [seats (mapv vec (util/read-input-lines 11))]
    ;; part1
    (println (count-occupied-seats (update-seats seats false)))
    ;; part1
    (println (count-occupied-seats (update-seats seats true)))))
