(ns sk.proutes.ordenes
  (:require [cheshire.core :refer [generate-string]]
            [selmer.parser :refer [render-file]]
            [noir.session :as session]
            [sk.models.crud :refer [db Query Save Delete]]
            [sk.models.grid :refer :all]
            [sk.models.util :refer [capitalize-words
                                    update-inventory
                                    format-date-internal
                                    parse-int
                                    fix-id
                                    get-session-id]]))

(defn ordenes [request]
  (render-file "sk/proutes/inventario/ordenes.html" {:title "Ordenes"
                                          :ok (get-session-id)}))
;; start grid-ordenes
(def search-columns
  ["orders.id"
   "DATE_FORMAT(orders.orden_fecha,'%d/%m/%y')"
   "CONCAT(productos.id,' | ', productos.p_etiqueta)"
   "orders.enviado_numero"
   "orders.nombre"
   "orders.apell_paterno"
   "orders.apell_materno"])

(def aliases-columns
  ["orders.id"
   "DATE_FORMAT(orders.orden_fecha,'%d/%m/%y') as orden_fecha"
   "CONCAT(productos.id,' | ', productos.p_etiqueta) as producto_id"
   "orders.enviado_numero"
   "orders.nombre"
   "orders.apell_paterno"
   "orders.apell_materno"])

(def grid-productos-join
  "JOIN productos on productos.id = orders.producto_id")

(defn grid-ordenes [{params :params}]
  (try
    (let [table "orders"
          scolumns (convert-search-columns search-columns)
          aliases aliases-columns
          join grid-productos-join
          search (grid-search (:search params nil) scolumns)
          order (grid-sort (:sort params nil) (:order params nil))
          offset (grid-offset (parse-int (:rows params)) (parse-int (:page params)))
          sql (grid-sql table aliases join search order offset)
          rows (grid-rows table aliases join search order offset)]
      (generate-string rows))
    (catch Exception e (.getmessage e))))
;; end grid-ordenes

;; start form-ordenes
(def form-ordenes-sql
  "select
   id,
   DATE_FORMAT(orden_fecha,'%m/%d/%y') as orden_fecha,
   producto_id,
   enviado_numero,
   nombre,
   apell_paterno,
   apell_materno
   FROM orders
   WHERE id = ?")

(defn form-ordenes [order_id]
  (let [row (Query db [form-ordenes-sql order_id])]
    (generate-string (first row))))
;; end form-ordenes

(defn ordenes-save [{params :params}]
  (let [id (fix-id (:id params))
        producto_id (:producto_id params)
        postvars {:id id
                  :nombre (capitalize-words (:nombre params))
                  :apell_paterno (capitalize-words (:apell_paterno params))
                  :apell_materno (capitalize-words (:apell_materno params))
                  :producto_id producto_id
                  :enviado_numero (:enviado_numero params)
                  :orden_fecha (format-date-internal (:orden_fecha params))}
        result (Save db :orders postvars ["id = ?" id])]
    (if (seq result)
      (do
        (update-inventory (parse-int producto_id))
        (generate-string {:success "correctamente processado!"}))
      (generate-string {:error "no se pudo processar!"}))))

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
        (generate-string {:success "removido apropiadamente!"}))
      (generate-string {:error "no se pudo remover!"}))))
;; End ordenes-delete
