(ns ClojureFinland.core
  (:require [ClojureFinland.html.page :as html]
            [ClojureFinland.css.styles :as css])
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
  (build!))

(comment
  (build-html!)
  (build-css!))
