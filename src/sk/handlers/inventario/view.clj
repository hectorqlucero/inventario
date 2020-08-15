(ns sk.handlers.inventario.view
  (:require [ring.util.anti-forgery :refer [anti-forgery-field]]
            [hiccup.page :refer [html5 include-js]]
            [sk.models.util :refer [build-table
                                    build-toolbar
                                    build-dialog
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
       [:th {:scope "col" :colspan 6} "Pedidos"]]
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

(def dialog-fields
  (list
   [:input {:type "hidden" :id "id" :name "id"}]
   (build-field
    {:id "p_nombre"
     :name "p_nombre"
     :class "easyui-textbox"
     :prompt "Nombre de la parte/producto"
     :data-options "label:'Producto:',
                     labelPosition:'top',
                     width:'100%',required:true"})
   (build-field
    {:id "n_parte"
     :name "n_parte"
     :class "easyui-textbox"
     :prompt "Numero de la parte"
     :data-options "label:'Parte#',
                     labelPosition:'top',
                     width:'100%',required:true"})
   (build-field
    {:id "p_etiqueta"
     :name "p_etiqueta"
     :class "easyui-textbox"
     :prompt "Etiqueta nombre aqui..."
     :data-options "label:'Etiqueta:',
                     labelPosition:'top',
                     width:'100%',required:true"})
   (build-field
    {:id "inv_inicio"
     :name "inv_inicio"
     :class "easyui-numberbox"
     :prompt "Inicio de inventario aqui..."
     :data-options "label:'Inicio:',
                     labelPosition:'top',
                     width:'100%',required:true,min:0,precision:0"})
   (build-field
    {:id "inv_recibido"
     :name "inv_recibido"
     :class "easyui-numberbox"
     :data-options "label:'Recibido',
                     labelPosition:'top',
                     width:'100%',required:true,disabled:true,precision:0"})
   (build-field
    {:id "inv_enviado"
     :name "inv_enviado"
     :class "easyui-numberbox"
     :data-options "label:'Enviado:',
                     labelPosition:'top',
                     width:'100%',required:true,disabled:true,precision:0"})
   (build-field
    {:id "inv_en_mano"
     :name "inv_en_mano"
     :class "easyui-numberbox"
     :data-options "label:'En mano:',
                     labelPosition:'top',
                     width:'100%',required:true,disabled:true,precision:0"})
   (build-field
    {:id "r_minimo"
     :name "r_minimo"
     :class "easyui-numberbox"
     :prompt "Minimo requerido aqui..."
     :data-options "label:'Minimo Requerido:',
                     labelPosition:'top',
                     width:'100%',required:true,min:0,precision:0"})))

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
   (build-table
    title
    "/inventario"
    (list
     [:th {:data-options "field:'p_nombre',sortable:true,fixed:false,width:100"} "Producto"]
     [:th {:data-options "field:'n_parte',sortable:true,fixed:false,width:100"
           :styler "styleParte"}   "Parte#"]
     [:th {:data-options "field:'p_etiqueta',sortable:true,fixed:true,width:100"} "Etiqueta"]
     [:th {:data-options "field:'inv_inicio',sortable:true,fixed:true,width:100"} "Inicio"]
     [:th {:data-options "field:'inv_recibido',sortable:true,fixed:true,width:100"} "Recibido"]
     [:th {:data-options "field:'inv_enviado',sortable:true,fixed:true,width:100"} "Enviado"]
     [:th {:data-options "field:'inv_en_mano',sortable:true,fixed:true,width:100"
           :styler "styleMinimo"}  "En Mano"]
     [:th {:data-options "field:'r_minimo',sortable:true,fixed:false,width:100"} "Minimo Requerido"]))
   (build-toolbar (toolbar-extra))
   (build-dialog title dialog-fields)))

(defn inventario-scripts []
  (list
   (include-js "/js/grid.js")
   [:script
    (str
     "
   function styleMinimo(val,row) {
    if(val < 1) {
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
    let dgOptions = dg.datagrid('options');
    dgOptions.view = detailview;
    dgOptions.detailFormatter = function() {
      return '<div class=\"ddv\" style=\"padding:5px 0;\"></div>';
    }
    dgOptions.onExpandRow = function(index, row) {
      let ddv = $(this).datagrid('getRowDetail', index).find('div.ddv');
      let url = \"/inventario/detalles/\" + row.id;
      ddv.panel({
        height:'auto',
        border:false,
        queryParams: {},
        method:'GET',
        href:url,
        onLoad:function() {
          dg.datagrid('fixDetailRowHeight', index);
        }
      });
      dg.datagrid('fixDetailRowHeight', index);
    }
   });
   ")]))
