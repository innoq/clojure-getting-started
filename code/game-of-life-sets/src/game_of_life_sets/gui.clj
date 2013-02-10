(ns game-of-life-sets.gui
  (:require [game-of-life-sets.core :as c]
            [game-of-life-sets.util.swing :as s])
  (:import [java.awt Color]))

(defn width-and-height [grid]
  (let [width (count (first grid))
        height (count grid)]
    [width height]))

(defn draw-cell [graphics cell]
  (doto graphics
    (.setColor (:color cell))
    (.fillRect (:x cell) (:y cell) (:size cell) (:size cell))))

(defn renderable-cells [grid scale]
  (let [[width height] (width-and-height grid)]
    (for [x (range width)
          y (range height)]
      (let [cell (get-in grid [y x])]
        {:color (if (= :living cell) Color/BLACK Color/WHITE)
         :x (* scale x)
         :y (* scale y)
         :size scale}))))

(defn draw-grid [scale graphics grid]
  (doseq [cell (renderable-cells grid scale)]
    (draw-cell graphics cell)))

(defn render [grid-holder]
  (let [[width height] (width-and-height @grid-holder)
        scale 10]
    (s/show (s/frame "Game of Life"
                     (s/canvas (* width scale) (* height scale)
                               grid-holder
                               (partial draw-grid scale))))))

(defn empty-grid
  [width height]
  (vec (repeat height
               (vec (repeat width nil)))))

(defn on-grid? [[x y] width height]
  (and (< -1 x width) (< -1 y height)))

(defn world->grid [world width height]
  (reduce
   (fn [grid [x y]]
     (assoc-in grid [y x] :living))
   (empty-grid width height)
   (filter #(on-grid? % width height) world)))

(defn run [world]
  (let [worlds (iterate c/next-world world)
        grids (map #(world->grid % 50 30) worlds)
        state (atom (first grids))]
    (.start (Thread. #(doseq [grid (rest grids)]
                        (Thread/sleep 500)
                        (reset! state grid))))
    (render state)))

;; blinker (run #{[10 10] [11 10] [12 10]})
;; glider (run #{[-3 -6] [-3 -5] [-3 -4] [-4 -4] [-5 -5]})