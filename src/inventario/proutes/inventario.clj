(ns inventario.proutes.inventario
  (:require [cheshire.core :refer [generate-string]]
            [inventario.models.crud :refer [db 
                                            build-postvars 
                                            Query 
                                            Save 
                                            Delete]]
            [inventario.models.grid :refer :all]
            [inventario.models.util :refer [parse-int
                                            fix-id
                                            get-session-id
                                            update-all-inventory]]
            [inventario.views.layout :refer :all]
            [inventario.views.inventario :refer [inventario-view 
                                                 inventario-scripts
                                                 detalles-view]]))

(defn inventario [request]
  (let [title "Inventario"
        ok (get-session-id)
        js (inventario-scripts)
        content (inventario-view title)]
    (application title ok js content)))

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

(defn grid-json [{params :params}]
  (try
    (doall (update-all-inventory))
    (let [table "productos"
          scolumns (convert-search-columns search-columns)
          aliases aliases-columns
          join ""
          search (grid-search (:search params nil) scolumns)
          order (grid-sort (:sort params nil) (:order params nil))
          offset (grid-offset (parse-int (:rows params)) (parse-int (:page params)))
          rows (grid-rows table aliases join search order offset)]
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

;; Start productos form
(def form-sql
  "SELECT
   *
   FROM productos
   WHERE id = ?")

(defn form-productos [producto_id]
  (let [row (first (Query db [form-sql producto_id]))]
    (generate-string row)))
;; End productos form

(defn productos-save [{params :params}]
  (let [id (fix-id (:id params))
        postvars (build-postvars "productos" params)
        result (Save db :productos postvars ["id = ?" id])]
    (if (seq result)
      (do
        (doall (update-all-inventory))
        (generate-string {:success "Correctamente Processado!"}))
      (generate-string {:error "No se pudo processar!"}))))

(defn productos-delete [{params :params}]
  (let [id (:id params nil)
        result (if-not (nil? id)
                 (Delete db :productos ["id = ?" id])
                 nil)]
    (if (seq result)
      (generate-string {:success "Removido apropiadamente!"})
      (generate-string {:error "No se pudo remover!"}))))
