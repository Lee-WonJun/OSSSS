(ns ossss.routes.home
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
  ["keyword"
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/:keyword" {:get {:handler handler}}]
   ["/" {:get {:handler handler}
         :post {:handler handler}}]])