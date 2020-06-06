(ns watcher
  (:require [juxt.dirwatch :as dirwatch]
            [clojure.java.io :as io]))

(defonce state (atom {}))

(defn start! [kw dir on-change]
  (swap! state assoc kw
          (dirwatch/watch-dir
           (fn [{:keys [file]}]
             (let [fpath (.getPath file)]
               (when (.isFile file) ; omit directories
                 (println "Changed file:" fpath)
                 (on-change fpath))))
           (io/file dir)))
  (println "Started watching" dir "with id" kw))

(defn stop! [kw]
  (dirwatch/close-watcher (kw @state))
  (println "Watcher" kw "stopped!"))
