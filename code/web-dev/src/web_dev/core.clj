(ns ring-demo.core
  (:use [ring.adapter.jetty :only [run-jetty]]
        [ring.util.response :only [redirect]]
        [ring.middleware.resource :only [wrap-resource]]
        [ring.middleware.file-info :only [wrap-file-info]]
        [compojure.core :only [GET POST PUT DELETE defroutes context]]
        [compojure.handler :only [api]]))

(defn webapp [req]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello, World!"})

(defonce server (run-jetty (var webapp) {:port 9090 :join? false}))