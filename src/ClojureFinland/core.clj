(ns ClojureFinland.core
  (:require [ClojureFinland.css.styles :as css]
            [ClojureFinland.html.page :as html]
            [clojure.java.io :as io])
  (:gen-class))

(def default-config
  {:html-target "./dist/index.html"
   :css-target  "./dist/styles.css"
   :dev         false})

(defonce config (atom default-config))

(defn build-html! []
  (spit (:html-target @config) (html/gen-page @config))
  (println "Wrote" (:html-target @config)))

(defn build-css! []
  (spit (:css-target @config) (css/gen-styles @config))
  (println "Wrote" (:css-target @config)))

(defn build! []
  (build-html!)
  (build-css!))

(defn -main
  "Builds html and css and spits them out to ./dist"
  [& _args]
  ;; Create target directories if they don't exist
  (io/make-parents (:html-target @config))
  (io/make-parents (:css-target @config))
  (build!))

(comment
  (build-html!)
  (build-css!))
