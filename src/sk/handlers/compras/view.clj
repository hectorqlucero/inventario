(ns sk.handlers.compras.view
  (:require [ring.util.anti-forgery :refer [anti-forgery-field]]
            [hiccup.page :refer [include-js]]
            [sk.models.util :refer [build-table
                                    build-dialog
                                    build-table-field
                                    build-toolbar
                                    build-field]]))

(def table-fields
  (list
    (build-table-field "Fecha" "field: 'compra_fecha', sortable: true")
    (build-table-field "Producto" "field: 'producto_id', sortable: true")
    (build-table-field "# Recibido" "field: 'num_recibido', sortable: true, align:'right'")
    (build-table-field "Provedor" "field: 'provedor_id', sortable: true")))

(def dialog-fields
  (list
    [:input {:type "hidden" :id "id" :name "id"}]
    (build-field
      {:id "producto_id"
       :name "producto_id"
       :class "easyui-combobox"
       :data-options "label:'Producto:',
                     labelPosition:'top',
                     width:'100%',required:true,method:'GET',url:'/table_ref/get_productos'"})
    (build-field
      {:id "compra_fecha"
       :name "compra_fecha"
       :class "easyui-datebox"
       :prompt "Fecha de compra aqui..."
       :data-options "label:'Fecha:',
                     labelPosition:'top',
                     width:'100%',required:true"})
    (build-field
      {:id "num_recibido"
       :name "num_recibido"
       :class "easyui-numberbox"
       :data-options "label:'Recibido:',
                     labelPosition:'top',
                     width:'100%',required:true,min:0,precision:0"})
    (build-field
      {:id "provedor_id"
       :name "provedor_id"
       :class "easyui-combobox"
       :data-options "label:'Provedor:',
                     labelPosition:'top',
                     width:'100%',required:true,method:'GET',url:'/table_ref/get_provedores'"})))

(defn compras-view [title]
  (list
    (anti-forgery-field)
    (build-table title "/compras" table-fields)
    (build-toolbar)
    (build-dialog title dialog-fields)))

(defn compras-scripts []
  (include-js "/js/grid.js"))
