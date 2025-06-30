(ns clow.core-test
  (:require [clojure.test :refer :all]
            [clow.core :refer :all]))

(deftest just-test
  (testing "Testing trial"
    (is (= 1 (- 2 1)) "One (in number) should be equal to 1")))
