(ns reg.core
  {:doc "linear regression"}
  (:use [clojure.tools.cli :only [cli]])
  (:require clojure.string)
  (:use clojure.tools.trace)
  (:require reg.misc)
  (:require reg.step)
  (:gen-class)
  )

;; (trace-ns 'reg.step)
;; (trace-vars reg.step/calc-one-step)
;; (trace-vars reg.step/reg)
;; (trace-vars reg.step/reg-aux)
;; (trace-vars reg.step/get-misclassified)
;; (trace-vars reg.step/update-w)
;; (trace-vars reg.step/is-misclassified)
;; (untrace-vars reg.step/calc-diff-prob)
;; (untrace-vars reg.step/calc-diff-prob-aux)

(defn call-calc [n cnt verbose pic]
  (if verbose
    (binding [*out* *err* reg.misc/*verbose* 'true]
      (time (reg.step/calc n cnt pic)))
    (reg.step/calc n cnt pic)))

(defn print-result [res]
  (println "res: " res))

(defn -main [& args]
  (let [opts (cli
              args
              ["-v" "--[no-]verbose" :default false]
              ["-p" "--[no-]picture" :default false]
              ["-c" "--cnt" "Count of experiments"
               :parse-fn #(Integer. %)
               :default 1]
              ["-n" "--n" "N" :parse-fn #(Integer. %)])
        [options _ _] opts
        res (call-calc (:n options)
                       (:cnt options)
                       (:verbose options)
                       (:picture options))
        ]
    (print-result res)))

