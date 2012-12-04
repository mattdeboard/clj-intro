(ns clj-intro.javainterop
  (:import [com.itextpdf.text.pdf PdfReader SimpleBookmark]))

(defmacro proxy-meta
  "This macro emits a function that can then be called with the appropriate
args to proxy a given class, extending it to implement the `IObj' interface.

The reason a macro is used instead of a function is because the first arg to
`(proxy)' is a collection, the first argument `(proxy)' is itself a macro. In
practical terms, this means that the args for `(proxy)' are evaluated at compile
 time, so they must be set then."
  [class]
  `(fn make#
     [infile# & [moremeta#]]
     (let [metadata# (merge {:filename infile#} moremeta#)]
       (proxy [~class clojure.lang.IObj] [infile#]
         (withMeta [newmeta#] (make# infile# newmeta#))
         (meta [] metadata#)))))

(defn pdf<-
  "Returns PdfReader instance. The returned instance has metadata attached
to access various data about the PDF, such as the filename of the PDF, the
number of pages, and so forth."
  [^String infile]
  (let [rdr ((proxy-meta PdfReader) infile)
        bm (SimpleBookmark/getBookmark rdr)
        ct (. rdr getNumberOfPages)]
    (vary-meta rdr assoc :pagecount ct :bookmarks bm)))
