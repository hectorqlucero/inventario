(ns sk.handlers.reportes.enviado.view)

(defn build-footer-th [row producto_id]
  (if (= (:producto_id row) producto_id)
    (list
     [:tr
      [:td {:style "font-weight: bold;"} "Total:"]
      [:td {:align "right" :style "font-weight: bold;"} (:total row)]
      [:td " "]
      [:td " "]
      [:td " "]])
    nil))

(defn build-body-td [row producto_id]
  (if (= (:producto_id row) producto_id)
    (list
     [:tr
      [:td (:orden_fecha row)]
      [:td {:align "right"} (:enviado_numero row)]
      [:td (:nombre row)]
      [:td (:apell_paterno row)]
      [:td (:apell_materno row)]])
    nil))

(defn build-headers-th [prow rows trows]
  (list
    [:tr
     [:th {:colspan 5} (:producto_id prow)]]
    (map #(build-body-td % (:producto_id prow)) rows)
    (map #(build-footer-th % (:producto_id prow)) trows)
    [:tr [:td {:colspan 5} " "]]))

(defn enviado-view [title prows rows trows]
  [:div.table-responsive.table-sm
   [:table.table.table-striped.w-auto
    [:caption {:style "caption-side: top;"} title]
    [:thead.thead-dark
     [:tr
      [:th {:scope "col"} "FECHA"]
      [:th {:scope "col"} "ENVIADO"]
      [:th {:scope "col"} "NOMBRE"]
      [:th {:scope "col"} "PATERNO"]
      [:th {:scope "col"} "MATERNO"]]]
    [:tbody
     (map #(build-headers-th % rows trows) prows)]]])
