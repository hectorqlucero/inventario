(ns inventario.views.consultas.recibido)

(defn build-footer-th [row producto_id]
  (if (= (:producto_id row) producto_id)
    [:tr
     [:td {:style "font-weight: bold;"} "Total:"]
     [:td {:align "right" :style "font-weight: bold;"} (:total row)]
     [:td " "]]))

(defn build-body-td [row producto_id]
  (if (= (:producto_id row) producto_id)
    [:tr
     [:td (:compra_fecha row)]
     [:td {:align "right"} (:num_recibido row)]
     [:td (:provedor_id row)]]))

(defn build-headers-th [prow rows trows]
  (list
    [:tr
     [:th {:colspan 4} (:producto_id prow)]]
    (map #(build-body-td % (:producto_id prow)) rows)
    (map #(build-footer-th % (:producto_id prow)) trows)
    [:tr [:td {:colspan 3} " "]]))

(defn recibido-view [title prows rows trows]
  [:div.table-responsive.table-sm
   [:table.table.table-striped.w-auto
    [:caption {:style "caption-side: top;"} title]
    [:thead.thead-dark
     [:tr
      [:th {:scope "col"} "FECHA"]
      [:th {:scope "col"} "RECIBIDO"]
      [:th {:scope "col"} "PROVEDOR"]]]
    [:tbody
     (map #(build-headers-th % rows trows) prows)]]])
