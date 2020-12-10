(ns aoc2020.d4
  (:require [aoc2020.util :as util]
            [clojure.set :as set]
            [clojure.string :as str]))

(def rules
  {"byr" #(<= 1920 (Integer/parseInt %) 2002)
   "iyr" #(<= 2010 (Integer/parseInt %) 2020)
   "eyr" #(<= 2020 (Integer/parseInt %) 2030)
   "hgt" #(let [[_ sz unit] (re-matches #"(\d+)(.+)" %)]
            (case unit
              "cm"  (<= 150 (Integer/parseInt sz) 193)
              "in"  (<= 59 (Integer/parseInt sz) 76)
              false))
   "hcl" #(re-matches #"#[0-9a-f]{6}" %)
   "ecl" #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"}
   "pid" #(re-matches #"\d{9}" %)})

(defn has-all-keys?
  [passport]
  (set/subset? (set (keys rules))
               (set (keys passport))))

(defn all-entries-valid?
  [passport]
  (every? (fn [[k v]]
            (if-let [rule (rules k)]
              (rule v)
              true))
          passport))

(defn day4
  []
  (let [input     (util/read-input 4)
        passports (map #(apply hash-map (str/split % #":|\s+"))
                       (str/split input #"\n\n"))]
    ;; part1
    (->> passports
         (filter has-all-keys?)
         count
         println)
    ;; part2
    (->> passports
         (filter (every-pred has-all-keys? all-entries-valid?))
         count
         println)))
