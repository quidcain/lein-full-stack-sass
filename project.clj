(defproject reframe-extra "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.520"]
                 [reagent "0.8.1"]
                 [re-frame "0.10.6"]
                 [secretary "1.2.3"]
                 [compojure "1.5.0"]
                 [yogthos/config "0.8"]
                 [ring "1.4.0"]]

  :plugins [[lein-cljsbuild "1.1.7"]
            [yogthos/lein-sass "0.1.8"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj" "src/cljs"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :figwheel {:css-dirs ["resources/public/css"]
             :ring-handler reframe-extra.handler/dev-handler}

  :sass {:source "sass"
         :target "resources/public/css"}
  
  :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "0.9.10"]
                   [day8.re-frame/re-frame-10x "0.3.7-react16"]
                   [day8.re-frame/tracing "0.5.1"]
                   [figwheel-sidecar "0.5.16"]
                   [cider/piggieback "0.3.5"]]

    :plugins      [[lein-figwheel "0.5.18"]]}
   :prod { :dependencies [[day8.re-frame/tracing-stubs "0.5.1"]]}
   :uberjar {:dependencies [[day8.re-frame/tracing-stubs "0.5.1"]]
             :omit-source  true
             :main         reframe-extra.server
             :aot          [reframe-extra.server]
             :uberjar-name "reframe-extra.jar"
             :prep-tasks   ["compile" ["cljsbuild" "once" "min"]["sass"]]}}

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs"]
     :figwheel     {:on-jsload "reframe-extra.core/mount-root"}
     :compiler     {:main                 reframe-extra.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true
                    :preloads             [devtools.preload
                                           day8.re-frame-10x.preload]
                    :closure-defines      {"re_frame.trace.trace_enabled_QMARK_" true
                                           "day8.re_frame.tracing.trace_enabled_QMARK_" true}
                    :external-config      {:devtools/config {:features-to-install :all}}}}

    {:id           "min"
     :source-paths ["src/cljs"]
     :jar true
     :compiler     {:main            reframe-extra.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}]})
