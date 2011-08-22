(ns proxy.web
  (:require [clj-json.core :as json])
  (:require [clj-http.client :as client])
  (:use ring.adapter.jetty)
  (:use ring.middleware.stacktrace)
  (:use ring.middleware.basic-auth)
  (:use clojure.contrib.io)
  (:use compojure.core))

(defn env [k]
  (System/getenv k))

(defn env! [k]
    (or (env k) (throw (Exception. (str "missing key " k)))))

(defn post-campfire [msg]
  (client/post
   (format "https://%s.campfirenow.com/room/%s/speak.json"
           (env! "CAMPFIRE_USER")
           (env! "CAMPFIRE_ROOM"))
  {:basic-auth [(env! "CAMPFIRE_APIKEY") "X"]
   :body (json/generate-string {:message msg})
   :content-type :json}))

(defn recv-clk [req]
  (let [msg (json/parse-string (slurp* (:body req)))]
    (post-campfire
       (format "node:%s tag:%s type:%s check:\"%s\" %s->%s dur:%s detail:\"%s\" see:http://bit.ly/r0CNTb"
           (get (get msg "node") "name")
           (str (get (get msg "node") "tags"))
           (get msg "type")
           (get msg "check")
           (get (get (get msg "state_change") "previous") "status")
           (get (get (get msg "state_change") "current") "status")
           (get (get (get msg "state_change") "current") "duration")
           (get (get (get msg "state_change") "current") "status_details")
           (get (get (get msg "state_change") "current") "time")))))

(defn router [req]
  (condp = (:uri req)
    "/recv/clk"
    (do (recv-clk req)
        {:status 200
         :headers {"Content-Type" "text/html"}
         :body (str "Hello World")})
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body (str "welcome")}))

(defn app [req]
  (router req))

(defn -main []
  (let [port (Integer/parseInt (System/getenv "PORT"))]
    (run-jetty app {:port port})))
