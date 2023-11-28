(ns ClojureFinland.html.page
  (:require [clojure.string :as string]
            [hiccup.page :as page]))

;;; Contents ;;;

(def main
  {:title "Clojure Finland"
   :code  "(require '[ClojureFinland.html.page :as page])"})

(def about
  {:title "About"
   :id    "about"
   :description
   {:code "(-> page/about :description :data)"
    :data "Clojure Finland is a community that promotes the use of
    Clojure in Finland. We organise meetups in Helsinki and
    Tampere.<br /><br />If you are new to Clojure, check the
    <a href=\"https://clojure.org/guides/getting_started\">guides at
    Clojure.org</a> and don’t hesitate to ask questions in e.g. #beginners
    or #clojure-finland channels in
    <a href=\"http://clojurians.net/\">Clojurians Slack</a> or
    #clojure channel in <a href=\"https://koodiklinikka.fi\">Koodiklinikka Slack</a>!"}})

(def companies
  {:title "Companies"
   :id    "companies"
   :description
   {:code "(-> page/companies :description :data)"
    :data "Companies that use Clojure in Finland."}
   :items
   {:code "(doseq [company (-> page/companies :items :data)] (prn company))"
    :data
    [{:name "Cartman Digital" :web "https://www.cartman.fi/"}
     {:name "Cloudpermit" :web "https://cloudpermit.com/"}
     {:name "Digitaalinen asuntokauppa DIAS" :web "https://dias.fi"}
     {:name "Discendum" :web "https://discendum.fi/"}
     {:name "Emblica" :web "https://emblica.fi"}
     {:name "Flowa" :web "https://www.flowa.fi"}
     {:name "Futurice" :web "https://futurice.com"}
     {:name "Gofore" :web "https://gofore.com"}
     {:name "IPRally" :web "https://www.iprally.com/"}
     {:name "Leanheat" :web "https://leanheat.com"}
     {:name "Metosin" :web "https://www.metosin.fi"}
     {:name "Netum" :web "https://www.netum.fi"}
     {:name "Nitor" :web "https://www.nitor.com"}
     {:name "Reaktor" :web "https://www.reaktor.com"}
     {:name "Remod" :web "https://remod.fi/"}
     {:name "Sharetribe" :web "https://www.sharetribe.com/"}
     {:name "Solita" :web "https://www.solita.fi"}
     {:name "Siili Solutions" :web "https://www.siili.com"}
     {:name "Taiste" :web "https://taiste.fi"}
     {:name "Tomorrow Tech" :web "https://tomorrow.fi"}
     {:name "Vincit" :web "https://www.vincit.fi"}
     {:name "Wunderdog" :web "https://wunder.dog"}
     {:name "YLE" :web "https://yle.fi/"}]}
   :text
   {:code "(-> page/companies :text :data)"
    ;; This is a bit ugly but I'll do it only once. ;)
    :data "Is your company missing from the list? <a
           href=\"#contact\">Contact us</a>."}})

(def people
  {:title "People"
   :id    "people"
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
   :id    "meetup-groups"
   :code  "(:data page/meetup-groups)"
   :data
   {:helsinki "https://www.meetup.com/Helsinki-Clojure-Meetup/events/"
    :tampere  "https://www.meetup.com/Tampere-Clojure-Meetup/"
    :bridge   "https://clojurebridge.fi/"}})

(def contact
  {:title "Contact"
   :id    "contact"
   :code  "(:data page/contact)"
   :data
   {:email   "<a href='mailto:clojurehelsinki@gmail.com'>clojurehelsinki@gmail.com</a>"
    :github  "https://github.com/ClojureFinland"
    :slack   [{:url      "https://koodiklinikka.fi/"
               :channel  "#clojure"
               :language "Finnish"}
              {:url      "https://join.slack.com/t/clojurians/shared_invite/zt-lsr4rn2f-jealnYXLHVZ61V2vdi15QQ"
               :channel  "#clojure-finland"
               :language "English"}]
    :twitter "https://twitter.com/clojurefinland"
    :youtube "https://www.youtube.com/channel/UC7GYPoytIg5R56V2Pn-xU9g/videos"}})

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

(declare table-output)

(defn map-output [m]
  (let [n-items (count m)]
    (map-indexed
     (fn [i [k v]]
       [:tr
        (into [:td {:aria-hidden "true"}] (if (= 0 i) "{" "&nbsp;"))
        [:th {:scope "row"} (keyword-output k)]
        [:td (let [column (cond
                            (map? v)  (table-output v)
                            (coll? v) (table-output v)
                            (link? v) (link v)
                            :else     (text-output v))]
               (cond-> column
                 (= i (dec n-items)) (into [[:span {:aria-hidden "true"} "}"]])))]])
     m)))

(defn table-output
  [data]
  [:table
   (cond->> data
     (map? data) (conj [])
     :always (map #(into [:tbody] (map-output %))))])

(defn section [{:keys [title id description code items data text]}]
  (cond-> [] ; use vector to conj at the end
    title       (conj [:h2 {:id id} ";; " title])
    code        (conj (code-output code))
    description (conj (section description))
    items       (conj (section items))
    data        (conj (cond
                        (map? data)  (table-output data)
                        (coll? data) (table-output data)
                        (link? data) (link data)
                        :else        (text-output data)))
    text        (conj (section text))
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

    [:link {:href "https://fonts.googleapis.com/css2?family=Cousine&display=swap" :rel "stylesheet"}]

    [:meta {:charset "utf-8"}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
    (page/include-css "styles.css")]

   [:body
    [:h1 {:id "clojure-finland"} ";;; " (:title main)]
    (code-output (:code main))
    (mapcat section [about companies #_people meetup-groups contact])]))
