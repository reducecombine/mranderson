(def project-version "0.5.4-SNAPSHOT")

(defproject thomasa/mranderson project-version
  :description "Dependency inlining and shadowing tool."
  :url "https://github.com/benedekfazekas/mranderson"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :eval-in :leiningen
  :java-source-paths ["java-src"]
  :javac-options ~(cond
                    (re-find #"^1[67]." (System/getProperty "java.version"))
                    ["-target" "1.7" "-source" "1.7"]
                    :else ["-target" "1.6" "-source" "1.6"])
  :filespecs [{:type :bytes :path "mranderson/project.clj" :bytes ~(slurp "project.clj")}]
  :dependencies [^:inline-dep [com.cemerick/pomegranate "0.4.0"]
                 ^:inline-dep [org.clojure/tools.namespace "1.1.0"]
                 ^:inline-dep [me.raynes/fs "1.4.6"]
                 ^:inline-dep [rewrite-clj "1.0.682-alpha"]
                 [org.clojure/clojure "1.10.3" :scope "provided"]
                 [com.googlecode.jarjar/jarjar "1.3"]
                 [org.pantsbuild/jarjar "1.7.2"]]
  :mranderson {:project-prefix "mranderson.inlined"}
  :profiles {:dev {:dependencies [[leiningen-core "2.9.1"]]}
             :eastwood {:plugins      [[jonase/eastwood "0.9.9"]]
                        :eastwood     {:exclude-linters [:no-ns-form-found]}}
             :mranderson-plugin {:plugins [[thomasa/mranderson ~project-version]]}
             :kaocha {:dependencies [[lambdaisland/kaocha "0.0-418"]
                                     [lambdaisland/kaocha-cloverage "0.0-32"]]}}
  :aliases {"kaocha" ["with-profile" "+kaocha" "run" "-m" "kaocha.runner"]
            "kaocha-watch" ["with-profile" "+kaocha" "run" "-m" "kaocha.runner" "--watch"]
            "kaocha-coverage" ["with-profile" "+kaocha" "run" "-m" "kaocha.runner" "--plugin" "cloverage"]})
