(ns sk.handlers.ordenes.view
  (:require [ring.util.anti-forgery :refer [anti-forgery-field]]
            [hiccup.page :refer [include-js]]
            [sk.models.util :refer [build-table
                                    build-dialog
                                    build-table-field
                                    build-toolbar
                                    build-field]]))

(def table-fields
  (list
    (build-table-field "Fecha de Pedido" "field: 'orden_fecha', sortable: true")
    (build-table-field "Producto" "field: 'producto_id', sortable: true")
    (build-table-field "# Enviados" "field: 'enviado_numero', sortable: true, align:'right'")
    (build-table-field "Nombre" "field: 'nombre', sortable: true")
    (build-table-field "Paterno" "field: 'apell_paterno', sortable: true")
    (build-table-field "Materno" "field: 'apell_materno', sortable: true")))

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
      {:id "orden_fecha"
       :name "orden_fecha"
       :class "easyui-datebox"
       :data-options "label:'Fecha:',
                     labelPosition:'top',
                     width:'100%',required:true"})
    (build-field
      {:id "enviado_numero"
       :name "enviado_numero"
       :class "easyui-numberbox"
       :data-options "label:'Enviado:',
                     labelPosition:'top',
                     width:'100%',required:true,min:0,precision:0"})
    (build-field
      {:id "nombre"
       :name "nombre"
       :class "easyui-textbox"
       :data-options "label:'Nombre:',
                     labelPosition:'top',
                     width:'100%',required:true"})
    (build-field
      {:id "apell_paterno"
       :name "apell_paterno"
       :class "easyui-textbox"
       :data-options "label:'Apellido Paterno:',
                     labelPosition:'top',
                     width:'100%',required:true"})
    (build-field
      {:id "apell_materno"
       :name "apell_materno"
       :class "easyui-textbox"
       :data-options "label:'Apellido Materno:',
                     labelPosition:'top',
                     width:'100%',required:false"})))

(defn ordenes-view [title]
  (list
    (anti-forgery-field)
    (build-table title "/ordenes" table-fields)
    (build-toolbar)
    (build-dialog title dialog-fields)))


(defn ordenes-scripts []
  (include-js "/js/grid.js"))
