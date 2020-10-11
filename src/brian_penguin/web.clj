(ns brian-penguin.web
  (:require [compojure.core :refer [defroutes GET ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]))

(defn hello-response []
  {:status 200
   :headers {"Content-Type" "text/html" }
   :body "<h1> HELLO WORLD </h1>"})

(defroutes web-app
  (GET "/hello" []
       (hello-response))
  (GET "/" []
       (hello-response))
  (ANY "*" []
       (route/not-found (slurp (io/resource "404.html")))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'web-app { :port port :join? false }))))

;; For Interactive development
;(def server (-main))
;(.stop server)
