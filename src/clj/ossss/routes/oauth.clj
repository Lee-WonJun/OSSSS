(ns ossss.routes.oauth
  (:require
    [ossss.layout :as layout]
    [ossss.db.core :as db]
    [clojure.java.io :as io]
    [ossss.middleware :as middleware]
    [ring.util.response]
    [ossss.config :refer [env]]
    [mount.core :refer [defstate]]

    [clj-oauth2.client :as oauth2]
    [ring.util.http-response :as response]))


(defstate github-oauth2 :start
  {:authorization-uri (env :authorize-uri)
   :access-token-uri (env :access-token-uri )
   :redirect-uri "http://localhost:3000/oauth/callback"
   :client-id (env :oauth-consumer-key)
   :client-secret (env :oauth-consumer-secret)
   :access-query-param :access_token
   :scope ["email"]
   :grant-type "authorization_code"
   :access-type "online"
   :approval_prompt ""})

(def auth-req
  (oauth2/make-auth-request github-oauth2))

(defn- github-access-token [request]
  (oauth2/get-access-token github-oauth2 (:params request) auth-req))

(defn handler [req]
  (println req)
  {:status 200, :body "ok"})

(defn oauth-routes []
  ["/oauth"
   ["/login" {:get handler}]
   ["/callback" {:get handler}]])
