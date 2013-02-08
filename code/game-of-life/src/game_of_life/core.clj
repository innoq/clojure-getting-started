(ns game-of-life.core
  "Simple implementation of Conway's Game of Life with a fixed world size.

  The world is represented by a vector of vectors, e.g. Blinker
  (http://en.wikipedia.org/wiki/File:Game_of_life_blinker.gif) looks like this:

  [[:dead :dead :dead :dead :dead]
   [:dead :dead :living :dead :dead]
   [:dead :dead :living :dead :dead]
   [:dead :dead :living :dead :dead]
   [:dead :dead :dead :dead :dead]]")

(defn next-cell
  "see http://en.wikipedia.org/wiki/Conway%27s_Game_of_Life#Rules"
  [cell living-neighbors-count]
  (let [combination [cell living-neighbors-count]
        living-combinations #{[:living 2] [:living 3] [:dead 3]}]
    (if (some #{combination} living-combinations)
      :living
      :dead)))

(defn living? [cell]
  (= :living cell))

(defn empty-world
  "creates an empty world of the specified width and height, e.g.
  (empty-world 3 2):
  [[:dead :dead :dead]
   [:dead :dead :dead]]"
[width height]
  (vec (repeat height
               (vec (repeat width :dead)))))

(defn populate
  "pos is a vector of x and y coordinate; [0 0] is the upper left corner of the world"
  [world pos]
  (let [[x y] pos]
    (assoc-in world [y x] :living)))

(defn neighbor-positions
  "surrounding positions of pos - does not include pos itself or any positions with negative indices"
  [pos]
  (let [[x y] pos]
    (for [dx (range -1 2)
          dy (range -1 2)
          :let [nx (+ x dx)
                ny (+ y dy)
                npos [nx ny]]
          :when (and (>= nx 0)
                     (>= ny 0)
                     (not= npos pos))]
      npos)))

(defn cell-at [world [x y]]
  (get-in world [y x]))

(defn count-living-neighbors [world pos]
  (->> (map (fn [npos]
              (cell-at world npos))
            (neighbor-positions pos))
       (filter living?)
       count))

(defn positions
  "sequence of all positions of the given world"
  [world]
  (let [height (count world)
        width (count (first world))]
    (for [x (range width)
          y (range height)]
      [x y])))

(defn update-world [world [x y] cell]
  (assoc-in world [y x] cell))

(defn next-world
  "creates a new world according to the game of life rules"
  [world]
  (reduce (fn [acc pos]
            (let [living-count (count-living-neighbors world pos)
                  new-cell (next-cell (cell-at world pos) living-count)]
              (update-world acc pos new-cell)))
          world
          (positions world)))