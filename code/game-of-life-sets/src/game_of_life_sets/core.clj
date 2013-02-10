(ns game-of-life-sets.core
  "set-based implementation of game of life

   the world is represented as a set of positions with living cells, e.g. #{[0 0] [1 0] [2 0]}
   is a representation of 'blinker'

   usage example:
   - (def world (create-world)) to create an empty world
   - (def blinker (populate-cells world [0 0] [1 0] [2 0])) to create 'blinker'
   - (next-world blinker) to obtain the next world"
  (:require [clojure.set :as s]))

(defn create-world []
  #{})

(defn populate-cells [world & positions]
  (into world positions))

(defn neighbors [[x y]]
  (set (for [dx [-1 0 1]
             dy [-1 0 1]
             :when (not= [dx dy] [0 0])]
         [(+ x dx) (+ y dy)])))

(defn living-neighbors [world pos]
  (s/intersection world (neighbors pos)))

(defn potentially-changing-positions [world]
  (s/union world
           (set (mapcat neighbors world))))

(defn living? [world pos]
  (world pos))

(defn next-world [world]
  (set (for [pos (potentially-changing-positions world)
             :let [lnc (count (living-neighbors world pos))]
             :when (or (= lnc 3)
                       (and (= lnc 2) (living? world pos)))]
         pos)))