(ns sk.handlers.ordenes.handler
  (:require [cheshire.core :refer [generate-string]] 
            [sk.models.crud :refer [db
                                    build-postvars
                                    Query
                                    Save
                                    Delete]]
            [sk.models.grid :as grid]
            [sk.models.util :refer [update-inventory
                                    parse-int
                                    fix-id
                                    get-session-id]]
            [sk.layout :refer [application]]
            [sk.handlers.ordenes.view :refer [ordenes-view ordenes-scripts]]))

(defn ordenes
  [_]
  (try
    (let [title "Pedidos"
          ok (get-session-id)
          js (ordenes-scripts)
          content (ordenes-view title)]
      (application title ok js content))
    (catch Exception e (.getMessage e))))

;; Start ordenes grid
(def search-columns
  ["orders.id"
   "DATE_FORMAT(orders.orden_fecha,'%m/%d/%Y')"
   "CONCAT(productos.id,' | ',productos.p_etiqueta)"
   "orders.enviado_numero"
   "orders.nombre"
   "orders.apell_paterno"
   "orders.apell_materno"])

(def aliases-columns
  ["orders.id"
   "DATE_FORMAT(orders.orden_fecha,'%m/%d/%Y') as orden_fecha"
   "CONCAT(productos.id,' | ', productos.p_etiqueta) as producto_id"
   "orders.enviado_numero"
   "orders.nombre"
   "orders.apell_paterno"
   "orders.apell_materno"])

(def grid-productos-join
  "JOIN productos on productos.id = orders.producto_id")

(defn ordenes-grid [{params :params}]
  (try
    (let [table "orders"
          scolumns (grid/convert-search-columns search-columns)
          aliases aliases-columns
          join grid-productos-join
          search (grid/grid-search (:search params nil) scolumns)
          order (grid/grid-sort (:sort params nil) (:order params nil))
          offset (grid/grid-offset (parse-int (:rows params)) (parse-int (:page params)))
          rows (grid/grid-rows table aliases join search order offset)]
      (generate-string rows))
    (catch Exception e (.getmessage e))))
;; End ordenes grid

;; start form-ordenes
(def form-ordenes-sql
  "select
   id,
   DATE_FORMAT(orden_fecha,'%m/%d/%Y') as orden_fecha,
   producto_id,
   enviado_numero,
   nombre,
   apell_paterno,
   apell_materno
   FROM orders
   WHERE id = ?")

(defn ordenes-form [order_id]
  (let [row (Query db [form-ordenes-sql order_id])]
    (generate-string (first row))))
;; end form-ordenes

(defn ordenes-save [{params :params}]
  (let [id (fix-id (:id params))
        producto_id (:producto_id params)
        postvars (build-postvars "orders" params)
        result (Save db :orders postvars ["id = ?" id])]
    (if (seq result)
      (do
        (update-inventory (parse-int producto_id))
        (generate-string {:success "Procesado con éxito!"}))
      (generate-string {:error "No se puede procesar!"}))))

;; Start ordenes-delete
(def ordenes-delete-sql
  "SELECT
   producto_id
   FROM orders
   WHERE id = ?")

(defn get-ordenes-producto-id [order_id]
  (:producto_id (first (Query db [ordenes-delete-sql order_id]))))

(defn process-ordenes-delete [id]
  (if-not (nil? id)
    (Delete db :orders ["id = ?" id])
    nil))

(defn ordenes-delete [{params :params}]
  (let [id (:id params nil)
        producto_id (get-ordenes-producto-id id)
        result (process-ordenes-delete id)]
    (if (seq result)
      (do
        (update-inventory (parse-int producto_id))
        (generate-string {:success "Eliminado con éxito!"}))
      (generate-string {:error "Incapaz de eliminar!"}))))
;; End ordenes-delete
