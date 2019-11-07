(ns inventario.views.inventario
  (:require [hiccup.page :refer [html5 
                                 include-js]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [inventario.models.util :refer [build-table
                                            build-dialog
                                            build-table-field
                                            build-toolbar
                                            build-field]]))

(defn detalles-view [compras-rows ordenes-rows]
  (html5
    [:div.table-responsive
     [:table.table.table-striped
      [:thead.thead-dark
       [:tr
        [:th {:scope "col" :colspan 4} "Compras"]]
       [:tr
        [:th {:scope "col"} "Fecha"]
        [:th {:scope "col"} "Producto"]
        [:th {:scope "col"} "Numero Recibido"]
        [:th {:scope "col"} "Provedor"]]]
      [:tbody
       (for [crow compras-rows]
         [:tr
          [:td (:compra_fecha crow)]
          [:td (:p_etiqueta crow)]
          [:td {:align "center"} (:num_recibido crow)]
          [:td (:provedor crow)]])]]]
    [:div.table-responsive
     [:table.table.table-striped
      [:thead.thead-dark
       [:tr
        [:th {:scope "col" :colspan 6} "Ordenes"]]
       [:tr
        [:th {:scope "col"} "Fecha"]
        [:th {:scope "col"} "Producto"]
        [:th {:scope "col"} "# Enviado"]
        [:th {:scope "col"} "Nombre"]
        [:th {:scope "col"} "Apellido Paterno"]
        [:th {:scope "col"} "Apellido Materno"]]]
      [:tbody
       (for [orow ordenes-rows]
         [:tr
          [:td (:orden_fecha orow)]
          [:td (:p_etiqueta orow)]
          [:td {:align "center"} (:enviado_numero orow)]
          [:td (:nombre orow)]
          [:td (:apell_paterno orow)]
          [:td (:apell_materno orow)]])]]]))

;; Start inventario
(def table-fields
  (list
    (build-table-field "Producto" "field: 'p_nombre', sortable: true")
    (build-table-field "Parte#" "field: 'n_parte', sortable: true" "styleParte")
    (build-table-field "Etiqueta" "field: 'p_etiqueta', sortable: true")
    (build-table-field "Inicio" "field: 'inv_inicio', sortable: true")
    (build-table-field "Recibido" "field: 'inv_recibido', sortable: true")
    (build-table-field "Enviado" "field: 'inv_enviado', sortable: true")
    (build-table-field "En Mano" "field: 'inv_en_mano', sortable: true" "styleMinimo")
    (build-table-field "Minimo Requerido" "field: 'r_minimo', sortable: true")))

(def dialog-fields
  (list
    [:input {:type "hidden" :id "id" :name "id"}]
    (build-field
      "Producto:"
      {:id "p_nombre"
       :name "p_nombre"
       :class "form-control easyui-textbox"
       :data-options "required:true"})
    (build-field
      "Parte#"
      {:id "n_parte"
       :name "n_parte"
       :class "form-control easyui-textbox"
       :data-options "required:true"})
    (build-field
      "Etiqueta:"
      {:id "p_etiqueta"
       :name "p_etiqueta"
       :class "form-control easyui-textbox"
       :data-options "required:true"})
    (build-field
      "Inicio:"
      {:id "inv_inicio"
       :name "inv_inicio"
       :class "form-control easyui-numberbox"
       :data-options "required:true,min:0,precision:0"})
    (build-field
      "Recibido:"
      {:id "inv_recibido"
       :name "inv_recibido"
       :class "form-control easyui-numberbox"
       :data-options "required:true,disabled:true,precision:0"})
    (build-field
      "Enviado:"
      {:id "inv_enviado"
       :name "inv_enviado"
       :class "form-control easyui-numberbox"
       :data-options "required:true,disabled:true,precision:0"})
    (build-field
      "En mano:"
      {:id "inv_en_mano"
       :name "inv_en_mano"
       :class "form-control easyui-numberbox"
       :data-options "required:true,disabled:true,precision:0"})
    (build-field
      "Minimo Requerido:"
      {:id "r_minimo"
       :name "r_minimo"
       :class "form-control easyui-numberbox"
       :data-options "required:true,min:0,precision:0"})))

(defn toolbar-extra []
  (list
    [:a {:href "#"
         :class "easyui-linkbutton"
         :style "background-color:gold;"
         :data-options "plain:true"} "Reordenar Inventario"]
    [:a {:href "#"
         :class "easyui-linkbutton"
         :style "background-color:red;"
         :data-options "plain:true"} "Inventario Negativo"]))


(defn inventario-view [title]
  (list
    (anti-forgery-field)
    (build-table title "/inventario/json/grid" table-fields)
    (build-toolbar (toolbar-extra))
    (build-dialog title dialog-fields)))

(defn inventario-scripts []
  [:script
   (str
     "
      function styleMinimo(val,row) {
        if(val < 1){
          return 'background-color:red;color:black;';
        } else {
          return val;
        }
      }

    function styleParte(val,row) {
      if(row.inv_en_mano < row.r_minimo) {
        return 'background-color:gold;color:black;';
      } else {
        return val;
      }
    }

    $(document).ready(function() {
      var dataGridOptions = dataGrid.datagrid('options');
      dataGridOptions.view = detailview;
      dataGridOptions.detailFormatter = function() {
        return '<div class=\"ddv\" style=\"padding:5px 0;\"></div>';
      };
      dataGridOptions.onExpandRow = function (index, row) {
        var ddv = $(this).datagrid('getRowDetail', index).find('div.ddv');
        var url = \"/inventario/detalles/\" + row.id;
        ddv.panel({
          height:'auto',
          border:false,
          cache:false,
          queryParams: {},
          method:'GET',
          href:url,
          onLoad:function() {
            dataGrid.datagrid('fixDetailRowHeight', index);
          }
        });
        dataGrid.datagrid('fixDetailRowHeight', index);
      }
    });
      ")])
;; End inventario
