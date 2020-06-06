(ns server
  (:import org.eclipse.jetty.server.Server
           org.eclipse.jetty.server.handler.ResourceHandler))

(defonce state (atom nil))

(defn start! [{:keys [port root]}]
  (let [server  (Server. port)
        handler (ResourceHandler.)]

    (doto handler
      (.setDirAllowed true)
      (.setResourceBase root))

    (doto server
      (.setHandler handler)
      (.start))

    (reset! state server)
    (println (str "Server started http://localhost:" port))))

(defn stop! []
  (.stop @state)
  (println "Server stopped!"))

(comment
  (start! {:port 8999 :root "./dist"})
  (stop!))
