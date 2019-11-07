(ns inventario.views.ordenes
  (:require [ring.util.anti-forgery :refer [anti-forgery-field]]
            [inventario.models.util :refer [build-table
                                            build-dialog
                                            build-table-field
                                            build-toolbar
                                            build-field]]))

(def table-fields
  (list
    (build-table-field "Fecha de Orden" "field: 'orden_fecha', sortable: true")
    (build-table-field "Producto" "field: 'producto_id', sortable: true")
    (build-table-field "# Enviados" "field: 'enviado_numero', sortable: true, align:'right'")
    (build-table-field "Nombre" "field: 'nombre', sortable: true")
    (build-table-field "Paterno" "field: 'apell_paterno', sortable: true")
    (build-table-field "Materno" "field: 'apell_materno', sortable: true")))

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
      {:id "orden_fecha"
       :name "orden_fecha"
       :class "form-control easyui-datebox"
       :data-options "required:true"})
    (build-field
      "Envidado:"
      {:id "enviado_numero"
       :name "enviado_numero"
       :class "form-control easyui-numberbox"
       :data-options "required:true,min:0,precision:0"})
    (build-field
      "Nombre:"
      {:id "nombre"
       :name "nombre"
       :class "form-control easyui-textbox"
       :data-options "required:true"})
    (build-field
      "Apellido Paterno:"
      {:id "apell_paterno"
       :name "apell_paterno"
       :class "form-control easyui-textbox"
       :data-options "required:true"})
    (build-field
      "Apellido Materno:"
      {:id "apell_materno"
       :name "apell_materno"
       :class "form-control easyui-textbox"
       :data-options "required:false"})))

(defn ordenes-view [title]
  (list
    (anti-forgery-field)
    (build-table title "/ordenes/json/grid" table-fields)
    (build-toolbar)
    (build-dialog title dialog-fields)))

(defn ordenes-scripts []
  nil)
