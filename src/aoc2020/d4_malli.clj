(ns aoc2020.d4-malli
  (:require [aoc2020.util :as util]
            [clojure.string :as str]
            [malli.core :as m]
            [malli.transform :as mt]))

(def Passport
  (m/schema
   [:map
    [:byr [:int {:min 1920 :max 2002}]]
    [:iyr [:int {:min 2010 :max 2020}]]
    [:eyr [:int {:min 2020 :max 2030}]]
    [:hgt [:or
           [:re #"^((1[5-8][0-9])|(19[0-3]))cm$"]
           [:re #"^((59)|(6[0-9])|(7[0-6]))in$"]]]
    [:hcl [:re #"^#[0-9a-f]{6}$"]]
    [:ecl [:enum "amb" "blu" "brn" "gry" "grn" "hzl" "oth"]]
    [:pid [:re #"^\d{9}$"]]]))

(def decode-passport
  (m/decoder Passport
             (mt/transformer
              mt/string-transformer
              (mt/key-transformer {:decode keyword}))))

(defn has-missing-key
  [passport]
  (->> (m/explain Passport passport)
       :errors
       (some (comp #{::m/missing-key} :type))))

(defn day4
  []
  (let [input (util/read-input 4)
        passports (->> (str/split input #"\n\n")
                       (map #(->> (str/split % #":|\s+")
                                  (apply hash-map)
                                  decode-passport)))]
    (doseq [pred [(complement has-missing-key)
                  (partial m/validate Passport)]]
      (->> passports
           (filter pred)
           count
           println))))

(comment
  (day4)

  )
