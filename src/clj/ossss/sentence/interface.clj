(ns ossss.sentence.interface
  (:require
   [ossss.sentence.core :as core]))

(defn sentences [keyword]
  (core/sentences keyword))


