(ns sk.handlers.barcodes.bar.handler
  (:require [sk.models.crud :refer [db Query]]
            [sk.models.util :refer [get-session-id zpl]]
            [clojure.java.io :as io]
            [ondrs.barcode :refer [generate write!]]
            [sk.layout :refer [application]]
            [sk.handlers.barcodes.bar.view :refer [barcodes-view]]))

;; Start barcode
(def temp-dir
  (let [dir (str (System/getProperty "java.io.tmpdir") "/barcodes/")]
    (.mkdir (io/file dir))
    dir))

(defn create-html [file]
  (str "<img src='" file "'>"))

(defn generate-barcode [id]
  (let [barcode (generate :code128 id)]
    (doto barcode
      (.setDrawingText false)
      (.setBarHeight 50)
      (.setBarWidth 2))
    barcode))

(defn create-barcode [id]
  (let [id (zpl id 8)
        barcode (generate-barcode id)]
    (write! barcode (str temp-dir id ".jpg"))
    (str "/uploads/" id ".jpg")))

(defn get-barcodes [_]
  (let [rows (Query db ["SELECT id,p_etiqueta FROM productos ORDER BY id"])
        title "Codigo de barras"
        ok (get-session-id)
        result (map #(assoc % :id (create-barcode (:id %))) rows)
        content (barcodes-view result)]
    (application title ok nil content)))
;; End barcode
