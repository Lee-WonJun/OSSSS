(ns ossss.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [ossss.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[ossss started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[ossss has shut down successfully]=-"))
   :middleware wrap-dev})
