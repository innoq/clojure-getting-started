(ns game-of-life.repl
  (:use game-of-life.core
        game-of-life.gui
        clojure.repl
        clojure.pprint))

(def blinker
  (-> (empty-world 30 20)
      (populate [15 9])
      (populate [15 10])
      (populate [15 11])))

(def toad
  (-> (empty-world 30 20)
      (populate [13 9])
      (populate [14 9])
      (populate [15 9])
      (populate [14 10])
      (populate [15 10])
      (populate [16 10])))

(def glider
  (-> (empty-world 30 20)
      (populate [3 0])
      (populate [3 1])
      (populate [3 2])
      (populate [2 2])
      (populate [1 1])))
