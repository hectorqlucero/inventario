(ns sk.handlers.barcodes.qr.handler
  (:require [sk.models.crud :refer [db Query]]
            [sk.models.util :refer [get-session-id zpl]]
            [clojure.java.io :as io]
            [clj.qrgen :refer [as-file from]]
            [sk.layout :refer [application]]
            [sk.handlers.barcodes.qr.view :refer [barcodes-view]]))

;; Start barcode
(def temp-dir
  (let [dir (str (System/getProperty "java.io.tmpdir") "/barcodes/")]
    (.mkdir (io/file dir))
    dir))

(defn create-html [file]
  (str "<img src='" file "'>"))

(defn generate-barcode [id]
  (let [barcode (as-file (from (str id)) (str (str id) ".png"))]
    barcode))

(defn copy-file [source-path dest-path]
  (io/copy (io/file source-path) (io/file dest-path)))

(defn create-barcode [id]
  (let [id (zpl id 8)]
    (generate-barcode id)
    (copy-file (io/file (from id)) (str temp-dir id ".png"))
    (str "/uploads/" id ".png")))

(defn get-barcodes [_]
  (let [rows (Query db ["SELECT id,p_etiqueta FROM productos ORDER BY id"])
        title "Codigo de barras"
        ok (get-session-id)
        result (map #(assoc % :id (create-barcode (:id %))) rows)
        content (barcodes-view result)]
    (application title ok nil content)))
;; End barcode
