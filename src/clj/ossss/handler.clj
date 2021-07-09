(ns ossss.handler
  (:require
   [ossss.middleware :as middleware]
   [ossss.layout :refer [error-page]]
   [ossss.routes.home :refer [home-routes]]
   [ossss.routes.oauth :refer [oauth-routes]]
   [ossss.routes.keyword :refer [keyword-routes]]
   [reitit.ring :as ring]
   [ring.middleware.content-type :refer [wrap-content-type]]
   [ring.middleware.webjars :refer [wrap-webjars]]
   [ossss.env :refer [defaults]]
   [mount.core :as mount]))

(mount/defstate init-app
  :start ((or (:init defaults) (fn [])))
  :stop  ((or (:stop defaults) (fn []))))

(mount/defstate app-routes
  :start
  (ring/ring-handler
    (ring/router
      [(home-routes)
       (oauth-routes)
       (keyword-routes)])
   (ring/redirect-trailing-slash-handler)
    (ring/routes
      (ring/create-resource-handler
        {:path "/"})
      (wrap-content-type
        (wrap-webjars (constantly nil)))
      (ring/create-default-handler
        {:not-found
         (constantly (error-page {:status 404, :title "404 - Page not found"}))
         :method-not-allowed
         (constantly (error-page {:status 405, :title "405 - Not allowed"}))
         :not-acceptable
         (constantly (error-page {:status 406, :title "406 - Not acceptable"}))})))
  ;; => #object[clojure.lang.AFunction$1 0xfd2daca "clojure.lang.AFunction$1@fd2daca"]
)

(defn app []
  (middleware/wrap-base #'app-routes))
