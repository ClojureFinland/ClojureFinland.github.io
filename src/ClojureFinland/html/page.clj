(ns ClojureFinland.html.page
  (:require [clojure.string :as string]
            [hiccup.page :as page]))

;;; Contents ;;;

(def description
  "This is Clojure Finland. We are a community that blalbalbalblaba...")

(def links
  {:github  "https://github.com/ClojureFinland"
   :twitter "https://twitter.com/clojurefinland"})

(def people-description
  "Following people are active members of the Clojure Finland community")

(def people
  [{:name    "Ykä"
    :github  "https://github.com/ykarikos"
    :twitter "https://twitter.com/ykarikos"}
   {:name    "Heimo"
    :github  "https://github.com/huima"
    :twitter "https://twitter.com/huima"}
   {:name    "Kimmo"
    :twitter "https://twitter.com/kimmokoskinen"
    :github  "https://github.com/viesti"}
   {:name    "Tommi"
    :github  "https://github.com/ikitommi"
    :twitter "https://twitter.com/ikitommi"}
   {:name   "Tuomo"
    :github "https://github.com/tvirolai"}
   {:name   "Valtteri"
    :github "https://github.com/vharmain"}])

(def contact
  {:email "onko.meillä@joku.osoite"
   :zulip "https://clojurians.zulipchat.com/#narrow/stream/173291-clojure-finland"
   :slack "#clojure-finland at Clojurians Slack"})

;;; Utils ;;;

(defn link? [s]
  (string/starts-with? s "http"))

;;; Components ;;;

(defn text-output [s]
  [:span "\"" s "\""])

(defn link [href]
  [:span "\"" [:a {:href href} href] "\""])

(defn keyword-output [k]
  [:span.keyword ":" k])

(defn map-output [m]
  [:div.map
   [:div.container
    [:div.item-first "{"]
    [:div.item
     [:table
      [:tbody
       (for [[k v] m]
         [:tr
          [:td (keyword-output k)]
          [:td (if (link? v) (link v) (text-output v))]])]]]
    [:div.item-last "}"]]])

;;; Main ;;;

(defn gen-page [{:keys [dev]}]
  (page/html5
   {:lang "en"}

   [:head
    [:title "Clojure Finland"]

    ;; Auto-refresh page every second in dev mode
    (when dev
      [:meta {:http-equiv "refresh" :content 1}])

    [:meta {:charset "utf-8"}]
    (page/include-css "styles.css")]

   [:body
    [:h1 ";;; Clojure Finland"]
    [:p "> (require '[ClojureFinland.html.page :as page])"]

    [:h2 ";; About"]
    [:p "> (prn (page/description))"]
    (text-output description)

    [:h2 ";; Links"]
    [:p "> (prn page/links)"]
    (map-output links)

    [:h2 ";; People"]
    [:p "> (prn page/people-description)"]
    (text-output people-description)
    [:p "> (doseq [p page/people] (prn p))"]
    (interpose
     [:div.separator]
     (for [p people]
       (map-output p)))

    [:h2 ";; Contact"]
    [:p "> (prn page/contact)"]
    (map-output contact)]))
