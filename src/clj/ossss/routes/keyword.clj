(ns ossss.routes.keyword
  (:require
   [ossss.layout :as layout]
   [ossss.db.core :as db]
   [clojure.java.io :as io]
   [ossss.middleware :as middleware]
   [ring.util.response]
   [ring.util.http-response :as response]
   [ossss.keyword.interface :as k]
   [ossss.sentence.interface :as s]))

(defn keyword-list [_]
  {:status 200, :body (k/keyword-list)})

(defn sentences [keyword]
  (s/sentences keyword))

(defn handler [_]
  {:status 200, :body "ok"})

(defn keyword-routes []
  ["/api"
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/keyword/:keyword"
    {:get (fn [keyword] {:status 200, :body (sentences keyword)})}]
   ["/keyword"
    {:get {:handler keyword-list}
     :post {:handler handler}}]])
;; => #'ossss.routes.keyword/keyword-routes
