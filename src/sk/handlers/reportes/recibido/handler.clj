(ns sk.handlers.reportes.recibido.handler
  (:require [sk.models.crud :refer [db Query]]
            [sk.models.util :refer [get-session-id]]
            [sk.layout :refer [application]]
            [sk.handlers.reportes.recibido.view :refer [recibido-view]]))

;; Start recibido
(def productos-sql
  "SELECT
   CONCAT(id,' | ',p_etiqueta) as producto_id
   FROM productos")

(def recibido-sql
  "SELECT
   compras.id,
   DATE_FORMAT(compras.compra_fecha,'%d/%m/%Y') as compra_fecha,
   CONCAT(productos.id,' | ',productos.p_etiqueta) AS producto_id,
   compras.num_recibido,
   provedores.provedor AS provedor_id
   FROM compras
   JOIN productos ON productos.id = compras.producto_id
   JOIN provedores ON provedores.id = compras.provedor_id
   ORDER BY compras.compra_fecha DESC")

(def recibido-totals-sql
  "SELECT
   CONCAT(productos.id,' | ',productos.p_etiqueta) AS producto_id,
   CAST(SUM(IFNULL(compras.num_recibido,0)) AS SIGNED) AS total
   FROM compras
   JOIN productos on productos.id = compras.producto_id
   GROUP BY compras.producto_id")

(defn recibido [_]
  (let [title "Recibido"
        rows (Query db recibido-sql)
        trows (Query db recibido-totals-sql)
        prows (Query db productos-sql)
        ok (get-session-id)
        js nil
        content (recibido-view title prows rows trows)]
    (application title ok js content)))
;; End recibido
