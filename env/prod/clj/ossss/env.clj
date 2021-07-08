(ns ossss.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[ossss started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[ossss has shut down successfully]=-"))
   :middleware identity})
