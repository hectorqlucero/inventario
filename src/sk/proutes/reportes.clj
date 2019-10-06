(ns sk.proutes.reportes
  (:require [cheshire.core :refer [generate-string]]
            [clojure.java.io :as io]
            [ondrs.barcode :refer :all]
            [selmer.parser :refer [render-file]]
            [noir.session :as session]
            [sk.models.crud :refer [db Query Save Delete]]
            [sk.models.grid :refer :all]
            [sk.models.util :refer [capitalize-words
                                    update-inventory
                                    format-date-internal
                                    parse-int
                                    fix-id
                                    get-session-id]])
  (:import (net.sourceforge.barbecue Barcode)))

(def temp-dir
  (let [dir (str (System/getProperty "java.io.tmpdir") "/barcode-test")]
    (.mkdir (io/file dir))
    dir))

(def o-stream (io/output-stream (str temp-dir "/stream")))

(def barcode (generate :code128 "000000001"))

(doto barcode
  (.setDrawingText false)
  (.setBarHeight 3000)
  (.setBarWidth 200)
  (.setResolution 300))

(write! barcode o-stream :png)
(write! barcode (str temp-dir "/gato.jpg"))

(defn reportes [{params :params}])
