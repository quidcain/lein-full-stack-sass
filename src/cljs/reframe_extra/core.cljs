(ns reframe-extra.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [reframe-extra.events :as events]
   [reframe-extra.routes :as routes]
   [reframe-extra.views :as views]
   [reframe-extra.config :as config]
   ))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (routes/app-routes)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
