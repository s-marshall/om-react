(ns todolist_react.test-runner
  (:require
   [cljs.test :refer-macros [run-tests]]
   [todolist_react.core-test]))

(enable-console-print!)

(defn runner []
  (if (cljs.test/successful?
       (run-tests
        'todolist_react.core-test))
    0
    1))
