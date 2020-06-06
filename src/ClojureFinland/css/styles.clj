(ns ClojureFinland.css.styles
  (:require [garden.core :as garden]))

(defn gen-styles [_]
  (garden/css
   [:body
    {:color            "green"
     :font-family      "Courier"
     :background-color "#0F0D01"
     :font-size        "14px"}]

   [:h1 :h2
    {:color "gray"}]

   [:.separator
    {:height "0.5em"}]

   [:.container
    {:display               "inline-grid"
     :grid-template-columns "1em min-content 1em"}]

   [:.item-first
    {:padding-top "0.25em"}]

   [:.item-last
    {:padding-bottom "0.25em"
     :align-self     "end"}]))
