(ns ossss.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [ossss.core-test]))

(doo-tests 'ossss.core-test)

