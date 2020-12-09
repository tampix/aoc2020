(ns aoc2020.core
  (:gen-class))

(defn- execute-day
  [day]
  (println "Day" day)
  (try
    (let [ns-sym (symbol (str "aoc2020.d" day))]
      (when-not (find-ns ns-sym)
        (require ns-sym))
      (let [day-fn (ns-resolve ns-sym (symbol (str "day" day)))]
        (time (apply day-fn []))))
    (catch Exception _
      (println "Not implemented yet.")))
  (println))

(defn -main
  [& _]
  (doseq [day (range 25)]
    (execute-day (inc day))))

(comment
  (-main)

  )
