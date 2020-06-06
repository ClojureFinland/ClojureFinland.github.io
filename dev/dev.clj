(ns dev
  (:require [ClojureFinland.core :as clojure-finland]
            [watcher]
            [server]))

(def html-src-dir "./src/ClojureFinland/html")
(def css-src-dir "./src/ClojureFinland/css")

(defn toggle-auto-refresh! []
  (swap! clojure-finland/config update :dev not)
  (clojure-finland/build!)
  (let [state (if (:dev @clojure-finland/config) "ON" "OFF")]
    (println "Auto-refresh is now" state)
    (when (:dev @clojure-finland/config)
      (println "Please refresh the page once for the change to take effect."))
    state))

(def config @clojure-finland/config)

;; Dev config
(def port 8889)
(def serve-dir "./dist")

(comment

  ;; Trigger build manually
  (clojure-finland/build!)

  ;; Start watching html source changes
  (watcher/start! :html html-src-dir (fn [fpath]
                                       (load-file fpath) ;load changes to repl
                                       (clojure-finland/build-html!)))

  ;; Start watching css source changes
  (watcher/start! :css css-src-dir (fn [fpath]
                                     (load-file fpath) ;load changes to repl
                                     (clojure-finland/build-css!)))

  ;; Toggle browser auto-refresh every 1 second
  (toggle-auto-refresh!)

  ;; Stop watchers
  (watcher/stop! :html)
  (watcher/stop! :css)

  ;; Start http server
  (server/start! {:port port :root serve-dir})

  ;; Stop http server
  (server/stop!)

  )
