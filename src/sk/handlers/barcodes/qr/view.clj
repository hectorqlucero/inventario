(ns sk.handlers.barcodes.qr.view
  (:require [hiccup.page :refer [include-css]]))

(defn barcodes-view [rows]
  (list
    (include-css "/css/barcodes.css")
    (for [row rows]
      [:ul.barcode
       [:li
        [:div
         [:img {:src (:id row)}]
         [:br]
         (:p_etiqueta row)]]])))
