(ns game-of-life-sets.core
  "set-based implementation of game of life

   the world is represented as a set of positions with living cells, e.g. #{[0 0] [1 0] [2 0]}
   is a representation of 'blinker'"
  (:require [clojure.set :as s]))

(defn create-world []
  #{})

(defn populate-cells [world & positions]
  (into world positions))

(defn living-neighbors [world pos neighbors-fn]
  (s/intersection world (neighbors-fn pos)))

(defn potentially-changing-positions [world neighbors-fn]
  (s/union world
           (set (mapcat neighbors-fn world))))

(defn living? [world pos]
  (world pos))

(defn next-world
  [neighbors-fn lives-on? world]
  (set (for [pos (potentially-changing-positions world neighbors-fn)
             :let [lnc (count (living-neighbors world pos neighbors-fn))]
             :when (lives-on? (living? world pos) lnc)]
         pos)))

(defn cellular-automaton [neighbors-fn lives-on?]
  "creates a fn that takes an initial world and returns an infinite lazy sequence of all
   subsequent world states
   
   neighbors-fn takes a pos and returns the set of neighbors
   lives-on? is a fn of two args:
       - a boolean if the cell lives
       - the number of living neighbors"
  (fn [initial-world]
    (rest (iterate (partial next-world neighbors-fn lives-on?)
                   initial-world))))

(defn moore-neighbors [[x y]]
  (set (for [dx [-1 0 1]
             dy [-1 0 1]
             :when (not= [dx dy] [0 0])]
         [(+ x dx) (+ y dy)])))

(defn game-of-life-rules [living living-neighbors-count]
  (or (= 3 living-neighbors-count)
      (and living (= 2 living-neighbors-count))))

(def game-of-life
  (cellular-automaton moore-neighbors game-of-life-rules))

;; (take 2 (game-of-life #{[0 0] [1 0] [2 0]}))