(ns sk.handlers.reportes.inventario.view)

(defn inventario-view [title rows]
  [:div.table-responsive.table-sm
   [:table.table.table-striped.w-auto
    [:caption {:style "caption-side: top;"} title]
    [:thead.thead-dark
     [:tr
      [:th {:scope "col"} "#"]
      [:th {:scope "col"} "ID"]
      [:th {:scope "col"} "PRODUCTO"]
      [:th {:scope "col"} "PARTE#"]
      [:th {:scope "col"} "ETIQUETA"]
      [:th {:scope "col"} "INICIO"]
      [:th {:scope "col"} "RECIBIDO"]
      [:th {:scope "col"} "ENVIADO"]
      [:th {:scope "col"} "EN MANO"]
      [:th {:scope "col"} "MINIMO REQUERIDO"]]]
    [:tbody
     (let [i (atom 0)]
       (for [row rows]
         [:tr
          [:td (swap! i inc)]
          [:td (:id row)]
          [:td (:p_nombre row)]
          (if (< (:inv_en_mano row) (:r_minimo row))
            [:td {:style "background-color: gold;"} (:n_parte row)]
            [:td (:n_parte row)])
          [:td (:p_etiqueta row)]
          [:td (:inv_inicio row)]
          [:td (:inv_recibido row)]
          [:td (:inv_enviado row)]
          (if (< (:inv_en_mano row) 1)
            [:td {:style "background-color: red;"} (:inv_en_mano row)]
            [:td (:inv_en_mano row)])
          [:td (:r_minimo row)]]))]]])
