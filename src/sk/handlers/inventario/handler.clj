(ns sk.handlers.inventario.handler
  (:require [cheshire.core :refer (generate-string)]
            [sk.models.crud :refer [db
                                    Query
                                    build-form-row
                                    build-form-save
                                    build-form-delete]]
            [sk.models.grid :as grid]
            [sk.models.util :refer [get-session-id
                                    update-all-inventory
                                    parse-int]]
            [sk.layout :refer [application]]
            [sk.handlers.inventario.view :refer [inventario-view
                                                 inventario-scripts
                                                 detalles-view]]))

(defn inventario
  [_]
  (try
    (let [title "Inventario"
          ok (get-session-id)
          js (inventario-scripts)
          content (inventario-view title)]
      (application title ok js content))
    (catch Exception e (.getMessage e))))

;; Start inventario grid
(def search-columns
  ["id"
   "p_nombre"
   "n_parte"
   "p_etiqueta"
   "inv_inicio"
   "inv_recibido"
   "inv_enviado"
   "inv_en_mano"
   "r_minimo"])

(def aliases-columns search-columns)

(defn inventario-grid [{params :params}]
  (try
    (doall (update-all-inventory))
    (let [table "productos"
          scolumns (grid/convert-search-columns search-columns)
          aliases aliases-columns
          join ""
          search (grid/grid-search (:search params nil) scolumns)
          order (grid/grid-sort (:sort params nil) (:order params nil))
          offset (grid/grid-offset (parse-int (:rows params)) (parse-int (:page params)))
          rows (grid/grid-rows table aliases join search order offset)]
      (generate-string rows))
    (catch Exception e (.getMessage e))))
;; End inventario grid

;; Start detalles
(def detalles-compras-sql
  "SELECT
   DATE_FORMAT(compras.compra_fecha,'%d/%m/%Y') as compra_fecha,
   productos.p_etiqueta,
   compras.num_recibido,
   provedores.provedor
   FROM compras
   JOIN productos on productos.id = compras.producto_id
   JOIN provedores on provedores.id = compras.provedor_id
   WHERE compras.producto_id = ?
   ORDER BY compras.compra_fecha DESC")

(def detalles-ordenes-sql
  "SELECT
   DATE_FORMAT(orders.orden_fecha,'%d/%m/%Y') as orden_fecha,
   productos.p_etiqueta,
   orders.enviado_numero,
   orders.nombre,
   orders.apell_paterno,
   orders.apell_materno
   FROM orders
   JOIN productos on productos.id = orders.producto_id
   WHERE orders.producto_id = ?
   ORDER BY orders.orden_fecha DESC")

(defn detalles [producto_id]
  (let [compras-rows (Query db [detalles-compras-sql producto_id])
        ordenes-rows (Query db [detalles-ordenes-sql producto_id])]
    (detalles-view compras-rows ordenes-rows)))
;; End detalles

(defn inventario-form
  [id]
  (try
    (let [table "productos"]
      (build-form-row table id))
    (catch Exception e (.getMessage e))))

(defn inventario-save
  [{params :params}]
  (try
    (let [table "productos"]
      (build-form-save params table))
    (catch Exception e (.getMessage e))))

(defn inventario-delete
  [{params :params}]
  (try
    (let [table "productos"]
      (build-form-delete params table))
    (catch Exception e (.getMessage e))))
