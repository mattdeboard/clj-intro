;; Sequences
;; Vector literal: []
;; List literal: '()
(drop 2 [1 2 3 4 5])

(take 9 (cycle [1 2 3 4]))

(interleave [:a :b :c :d :e] [1 2 3 4 5])

(partition 3 [1 2 3 4 5 6 7 8 9])

(map vector [:a :b :c :d :e] [1 2 3 4 5])

;; old and busted
(apply str (interpose \, "asdf"))
;; new hotness
(clojure.string/join \, "asdf")

(reduce + (range 100))

;; ---

;; Maps and sets
;; Map literal: {}
;; Set literal: #{}
(def m {:a 1 :b 2 :c 3})
;; Compare:
(m :b)
(:b m)

(keys m)

(assoc m :d 4 :c 42)

(merge-with + m {:a 2 :b 3})

;; Equivalent to `from clojure.set import *' in Python. 
(use 'clojure.set)

(union #{:a :b :c} #{:c :d :e})

(join #{{:a 1 :b 2 :c 3} {:a 1 :b 21 :c 42}}
      #{{:a 1 :b 2 :e 5} {:a 1 :b 21 :d 4}})

;; Concurrency
;; Atoms
;; Here's a bit of data about a guy named Bob.
(def bob (atom {:age 40 :hometown "Burbank, CA" :pets true}))
bob
@bob

(swap! bob update-in [:age] - 1)

;; Compare:
bob
@bob

;; Refs
(def foo (ref {:a "fred" :b "ethel" :c 42 :d 17 :e 6}))
@foo
(assoc @foo :a "lucy")
@foo

