(ns inventario.views.compras
  (:require [hiccup.page :refer [include-js]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [inventario.models.util :refer [build-table
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
      "Producto:"
      {:id "producto_id"
       :name "producto_id"
       :class "form-control easyui-combobox"
       :data-options "required:true,method:'GET',url:'/table_ref/get_productos'"})
    (build-field
      "Fecha:"
      {:id "compra_fecha"
       :name "compra_fecha"
       :class "form-control easyui-datebox"
       :data-options "required:true"})
    (build-field
      "Recibido:"
      {:id "num_recibido"
       :name "num_recibido"
       :class "form-control easyui-numberbox"
       :data-options "required:true,min:0,precision:0"})
    (build-field
      "Provedor:"
      {:id "provedor_id"
       :name "provedor_id"
       :class "form-control easyui-combobox"
       :data-options "required:true,method:'GET',url:'/table_ref/get_provedores'"})))

(defn compras-view [title]
  (list
    (anti-forgery-field)
    (build-table title "/compras/json/grid" table-fields)
    (build-toolbar)
    (build-dialog title dialog-fields)))

(defn compras-scripts []
  nil)
