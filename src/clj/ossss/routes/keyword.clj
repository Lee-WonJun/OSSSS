(ns ossss.routes.keyword
  (:require
   [ossss.layout :as layout]
   [ossss.db.core :as db]
   [clojure.java.io :as io]
   [ossss.middleware :as middleware]
   [ring.util.response]
   [ring.util.http-response :as response]))

(defn handler [_]
  {:status 200, :body "ok"})

(defn keyword-routes []
  ["/api"
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/keyword/:keyword" {:get {:handler handler}}]
   ["/keyword" {:get {:handler handler
               :name ::keyword-get}
         :post {:handler handler}}]])
;; => #'ossss.routes.keyword/keyword-routes
