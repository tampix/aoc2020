(ns aoc2020.d7
  (:require [aoc2020.util :as util]))

(defn parse
  [lines]
  (into {}
        (map (juxt (partial re-find #"^\w+ \w+")
                   #(into {}
                          (map (fn [[_ n bag]]
                                 [bag (Integer/parseInt n)]))
                          (re-seq #"(\d+) (\w+ \w+)" %))))
        lines))

(defn bag-containing-seq
  [graph bag]
  (->> (keys graph)
       (filter #(get-in graph [% bag]))
       (mapcat #(bag-containing-seq graph %))
       (cons bag)
       (lazy-seq)))

(defn count-bag-contained
  [graph bag]
  (->> (get graph bag)
       (map (fn [[b n]]
              (* n (inc (count-bag-contained graph b)))))
       (reduce +)))

(defn day7
  []
  (let [graph (parse (util/read-input-lines 7))]
    ;; part1
    (->> (bag-containing-seq graph "shiny gold")
         set
         count
         dec ;; remove "shiny gold" itself
         println)
    ;; part2
    (println (count-bag-contained graph "shiny gold"))))
