(ns sk.handlers.reportes.enviado.handler
  (:require [sk.models.crud :refer [db Query]]
            [sk.models.util :refer [get-session-id]]
            [sk.layout :refer [application]]
            [sk.handlers.reportes.enviado.view :refer [enviado-view]]))

;; Start enviado
(def productos-sql
  "SELECT
   CONCAT(id,' | ',p_etiqueta) as producto_id
   FROM productos")

(def enviado-sql
  "SELECT
   orders.id,
   DATE_FORMAT(orders.orden_fecha,'%d/%m/%Y') as orden_fecha,
   CONCAT(productos.id,' | ',productos.p_etiqueta) as producto_id,
   orders.enviado_numero,
   orders.nombre,
   orders.apell_paterno,
   orders.apell_materno
   FROM orders
   JOIN productos on productos.id = orders.producto_id
   ORDER BY orders.orden_fecha DESC")

(def enviado-totals-sql
  "SELECT
   CONCAT(productos.id,' | ',productos.p_etiqueta) AS producto_id,
   CAST(SUM(IFNULL(orders.enviado_numero,0)) AS SIGNED) AS total
   FROM orders
   JOIN productos ON productos.id = orders.producto_id
   GROUP BY orders.producto_id")

(defn enviado [_]
  (let [title "Enviado"
        rows (Query db enviado-sql)
        trows (Query db enviado-totals-sql)
        prows (Query db productos-sql)
        ok (get-session-id)
        js nil
        content (enviado-view title prows rows trows)]
    (application title ok js content)))
;; End enviado
