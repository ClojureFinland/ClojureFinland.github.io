(ns ClojureFinland.html.page
  (:require [clojure.string :as string]
            [hiccup.page :as page]))

;;; Contents ;;;

(def main
  {:title "Clojure Finland"
   :code  "(require '[ClojureFinland.html.page :as page])"})

(def about
  {:title "About"
   :description
   {:code "(-> page/about :description :data)"
    :data "This is Clojure Finland. We are a community that
   blalbalbalblaba..."}})

(def companies
  {:title "Companies"
   :description
   {:code "(-> page/companies :description :data)"
    :data "Companies that use Clojure in Finland"}
   :items
   {:code "(doseq [company (-> page/companies :items :data)] (prn company))"
    :data
    [{:name "Emblica" :web "https://emblica.fi"}
     {:name "Evolta" :web "https://evolta.fi"}
     {:name "Flowa" :web "https://www.flowa.fi"}
     {:name "Futurice" :web "https://futurice.com"}
     {:name "Gofore" :web "https://gofore.com"}
     {:name "Leanheat" :web "https://leanheat.com"}
     {:name "Metosin" :web "https://www.metosin.fi"}
     {:name "Nitor" :web "https://www.nitor.com"}
     {:name "Reaktor" :web "https://www.reaktor.com"}
     {:name "Solita" :web "https://www.solita.fi"}
     {:name "Siili Solutions" :web "https://www.siili.com"}
     {:name "Tomorrow Tech" :web "https://tomorrow.fi"}
     {:name "Wunderdog" :web "https://wunder.dog"}
     {:name "YLE" :web "https://yle.fi/"}]}})

(def people
  {:title "People"
   :description
   {:code "(-> page/people :description :data)"
    :data "Following people are active members of the Clojure
    Finland community"}
   :items
   {:code "(-> page/people :items :data)"
    :data
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
      :github "https://github.com/vharmain"}]}})

(def meetup-groups
  {:title "Meetup Groups"
   :code  "(:data page/meetup-groups)"
   :data
   {:helsinki "https://www.meetup.com/Helsinki-Clojure-Meetup/events/"
    :tampere  "https://www.meetup.com/Tampere-Clojure-Meetup/"}})

(def contact
  {:title "Contact"
   :code  "(:data page/contact)"
   :data
   {:github  "https://github.com/ClojureFinland"
    :twitter "https://twitter.com/clojurefinland"
    :email   "onko.meillä@joku.osoite"
    :youtube "https://www.youtube.com/channel/UCfGBHLpNCBkRR-3eAbMoWcw"
    :zulip   "https://clojurians.zulipchat.com/#narrow/stream/173291-clojure-finland"}})

;;; Utils ;;;

(defn link? [s]
  (string/starts-with? s "http"))

;;; Components ;;;

(defn text-output [s]
  [:span "\"" s "\""])

(defn code-output [s]
  [:p.code "> " s])

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
          [:td (cond
                 (map? v)  (map-output v)
                 (link? v) (link v)
                 :else     (text-output v))]])]]]
    [:div.item-last "}"]]])

(defn section [{:keys [title description code items data]}]
  (cond-> [] ; use vector to conj at the end
    title       (conj [:h2 ";; " title])
    code        (conj (code-output code))
    description (conj (section description))
    items       (conj (section items))
    data        (conj (cond
                        (map? data)  (map-output data)
                        (coll? data) (->> data
                                          (map map-output)
                                          (interpose [:div.separator]))
                        (link? data) (link data)
                        :else        (text-output data)))
    :always     seq)) ; convert to seq for Hiccup

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
    [:h1 ";;; " (:title main)]
    (code-output (:code main))
    (mapcat section [about companies #_people meetup-groups contact])]))
