(ns ossss.routes.oauth
  (:require
    [ossss.layout :as layout]
    [ossss.db.core :as db]
    [clojure.java.io :as io]
    [ossss.middleware :as middleware]
    [ring.util.response]
    [ossss.config :refer [env]]
    [mount.core :refer [defstate]]
    [clj-http.client :as http]
    [clj-oauth2.client :as oauth2]
    [ring.util.http-response :as response]))


(defstate github-oauth2 :start
  {:authorization-uri (env :authorize-uri)
   :access-token-uri (env :access-token-uri )
   :redirect-uri "http://localhost:3000/oauth/callback"
   :client-id (env :oauth-consumer-key)
   :client-secret (env :oauth-consumer-secret)
   :access-query-param :access_token
   :scope ["user:email"]
   :grant-type "authorization_code"
   :access-type "online"
   :approval_prompt ""})

(defn auth-req []
  (oauth2/make-auth-request github-oauth2))

(defn- github-access-token [params]
  (oauth2/get-access-token github-oauth2 params (auth-req)))

(defn oauth-callback
  [{:keys [session params]}]
  (println session)
  (println params)
  (if (:denied params)
    {:status 200, :body "nok"}
    {:status 200, :body (if-let [access-token (github-access-token params)]
                          (do (println access-token)
                              (http/get "https://api.github.com/user/public_emails" {:oauth-token (:access-token access-token)})
                              ))}))

(defn handler [req]
  (println req)
  {:status 200, :body "ok"})

(defn oauth-routes []
  ["/oauth"
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/oauth-link" {:get (fn [_] {:status 200, :body {:link (:uri (auth-req))}})}]
   ["/callback" {:get oauth-callback}]])

