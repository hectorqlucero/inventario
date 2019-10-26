(ns sk.proutes.compras
  (:require [cheshire.core :refer [generate-string]]
            [selmer.parser :refer [render-file]]
            [sk.models.crud :refer [db 
                                    build-postvars 
                                    Query 
                                    Save 
                                    Delete]]
            [sk.models.grid :refer :all]
            [sk.models.util :refer [update-inventory
                                    parse-int
                                    fix-id
                                    get-session-id]]))

(defn compras [request]
  (render-file "sk/proutes/inventario/compras.html" {:title "Compras"
                                          :ok (get-session-id)}))
;; start grid-compras
(def search-columns
  ["compras.id"
   "DATE_FORMAT(compras.compra_fecha,'%d/%m/%y')"
   "CONCAT(productos.id,' | ', productos.p_etiqueta)"
   "compras.num_recibido"
   "provedores.provedor"])

(def aliases-columns
  ["compras.id"
   "DATE_FORMAT(compras.compra_fecha,'%d/%m/%y') as compra_fecha"
   "CONCAT(productos.id,' | ', productos.p_etiqueta) as producto_id"
   "compras.num_recibido"
   "provedores.provedor as provedor_id"])

(defn grid-compras [{params :params}]
  (try
    (let [table "compras"
          scolumns (convert-search-columns search-columns)
          aliases aliases-columns
          join (str "join productos on productos.id = compras.producto_id"
                    " "
                    "join provedores on provedores.id = compras.provedor_id")
          search (grid-search (:search params nil) scolumns)
          order (grid-sort (:sort params nil) (:order params nil))
          offset (grid-offset (parse-int (:rows params)) (parse-int (:page params)))
          rows (grid-rows table aliases join search order offset)]
      (generate-string rows))
    (catch Exception e (.getmessage e))))
;; end grid-compras

;; start form-compras
(def form-compras-sql
  "select
   id,
   DATE_FORMAT(compra_fecha,'%m/%d/%y') as compra_fecha,
   producto_id,
   num_recibido,
   provedor_id
   from compras
   where id = ?")

(defn form-compras [compras_id]
  (let [row (Query db [form-compras-sql compras_id])]
    (generate-string (first row))))
;; end form-compras

(defn compras-save [{params :params}]
  (let [id (fix-id (:id params))
        producto_id (:producto_id params)
        postvars (build-postvars "compras" params)
        result (Save db :compras postvars ["id = ?" id])]
    (if (seq result)
      (do
        (update-inventory (parse-int producto_id))
        (generate-string {:success "correctamente processado!"}))
      (generate-string {:error "no se pudo processar!"}))))

;; Start compras-delete
(def compras-delete-sql
  "SELECT
   producto_id
   FROM compras
   WHERE id = ?")

(defn get-compras-producto-id [compras_id]
  (:producto_id (first (Query db [compras-delete-sql compras_id]))))

(defn process-compras-delete [id]
  (if-not (nil? id)
    (Delete db :compras ["id = ?" id])
    nil))

(defn compras-delete [{params :params}]
  (let [id (:id params nil)
        producto_id (get-compras-producto-id id)
        result (process-compras-delete id)]
    (if (seq result)
      (do
        (update-inventory (parse-int producto_id))
        (generate-string {:success "removido apropiadamente!"}))
      (generate-string {:error "no se pudo remover!"}))))
;; End compras-delete
