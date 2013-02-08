(ns game-of-life.core-test
  (:use clojure.test
        game-of-life.core))

(deftest test-next-cell
  (testing "cell rules"
    (is (= :dead (next-cell :living 0)))
    (is (= :dead (next-cell :living 1)))
    (is :living (next-cell :living 3))
    (is :living (next-cell :dead 3))
    (is :dead (next-cell :living 6))))

(deftest test-empty-world
  (is [[:dead :dead :dead]
       [:dead :dead :dead]] (empty-world 3 2)))

(deftest test-neighbor-positions
  (are [x y] (= x y)
       [[0 1] [1 0] [1 1]] (neighbor-positions [0 0])
       [[4 4] [4 5] [4 6] [5 4] [5 6] [6 4] [6 5] [6 6]] (neighbor-positions [5 5])))

(deftest test-next-world
  (are [x y] (= x y)
       [[:dead :dead :dead]
        [:living :living :living]
        [:dead :dead :dead]]
       (next-world [[:dead :living :dead]
                    [:dead :living :dead]
                    [:dead :living :dead]])
       [[:dead :living :dead]
        [:dead :living :dead]
        [:dead :living :dead]]
       (next-world [[:dead :dead :dead]
                    [:living :living :living]
                    [:dead :dead :dead]])))

(deftest integration
  (is (= [[:dead :living :dead]
          [:dead :living :dead]
          [:dead :living :dead]]
         (-> (empty-world 3 3)
             (populate [0 1])
             (populate [1 1])
             (populate [2 1])
             next-world))))