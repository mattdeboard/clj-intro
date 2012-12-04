(ns clj-intro.agents
  (:use [clojure.java.shell :only [sh]]
        [clojure.string]))

(def folder "/home/matt/clojure-presentation")

(defn touch
  [pagenum]
  (let [filename (clojure.string/join "." [(str pagenum) "txt"])]
    (sh "touch" (clojure.string/join "/" [folder filename]))
    filename))

(defn make-files
  "Use agents for non-blocking (asynchronous) and uncoordinated creation
of text files. Uncoordinated is ok here since the text files have nothing
to do with each other."
  []
  (let [page-range (range 1 6)
        agents (doall (map #(agent (str %)) page-range))]
    ;; Use `send-off' here as `send' is not appropriate for IO
    (doseq [a agents] (send-off a touch))
    ;; Block for up to 10 seconds while awaiting the completion of the
    ;; previous operation. In this case, if we didn't `await-for', the
    ;; `make-files' fn would return its value immediately: the deref'd
    ;; agent values. Since we do however the return value is the result
    ;; of the `touch' fn.
    ;; (apply await-for 10000 agents)
    (doall (map #(deref %) agents))))

(make-files)

