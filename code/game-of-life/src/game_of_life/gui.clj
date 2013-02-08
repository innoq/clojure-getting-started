(ns game-of-life.gui
  (:require [game-of-life.util.swing :as s]
            [game-of-life.core :as c])
  (:import [java.awt Color]))

(defn width-and-height [world]
  (let [width (count (first world))
        height (count world)]
    [width height]))

(defn draw-cell [graphics cell]
  (doto graphics
    (.setColor (:color cell))
    (.fillRect (:x cell) (:y cell) (:size cell) (:size cell))))

(defn renderable-cells [world scale]
  (let [[width height] (width-and-height world)]
    (for [x (range width)
          y (range height)]
      (let [cell (c/cell-at world [x y])]
        {:color (if (c/living? cell) Color/BLACK Color/WHITE)
         :x (* scale x)
         :y (* scale y)
         :size scale}))))

(defn draw-world [scale graphics world]
  (doseq [cell (renderable-cells world scale)]
    (draw-cell graphics cell)))

(defn run [world]
  (let [scale 10
        [width height] (width-and-height world)
        state-holder (atom world)]
    (.start (Thread. (fn []
                       (Thread/sleep 500)
                       (swap! state-holder c/next-world)
                       (recur))))
    (s/show (s/frame "Game of Life"
                     (s/canvas (* width scale) (* height scale)
                               state-holder
                               (partial draw-world scale))))))