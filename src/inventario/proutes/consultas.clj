(ns inventario.proutes.consultas
  (:require [inventario.models.crud :refer [db Query]]
            [inventario.models.util :refer [get-session-id]]
            [inventario.views.layout :refer [application]]
            [inventario.views.consultas.inventario :refer [inventario-view]]
            [inventario.views.consultas.recibido :refer [recibido-view]]
            [inventario.views.consultas.enviado :refer [enviado-view]]
            [inventario.views.consultas.reordenar :refer [reordenar-view]]))

;; Start inventario
(def inventario-sql
  "SELECT 
   id,
   p_nombre,
   n_parte,
   p_etiqueta,
   inv_inicio,
   inv_recibido,
   inv_enviado,
   inv_en_mano,
   r_minimo
   FROM productos
   ORDER BY p_etiqueta")

(defn inventario [request]
  (let [title "Inventario"
        rows (Query db inventario-sql)
        ok (get-session-id)
        content (inventario-view title rows)
        js nil]
    (application title ok js content)))
;; End inventario

(def productos-sql
  "SELECT
   CONCAT(id,' | ',p_etiqueta) as producto_id
   FROM productos")

;; Start recibido
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

(defn recibido [request]
  (let [title "Recibido"
        rows (Query db recibido-sql)
        trows (Query db recibido-totals-sql)
        prows (Query db productos-sql)
        ok (get-session-id)
        js nil
        content (recibido-view title prows rows trows)]
    (application title ok js content)))
;; End recibido

;; Start enviado
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

(defn enviado [request]
  (let [title "Enviado"
        rows (Query db enviado-sql)
        trows (Query db enviado-totals-sql)
        prows (Query db productos-sql)
        ok (get-session-id)
        js nil
        content (enviado-view title prows rows trows)]
    (application title ok js content)))
;; End enviado

;; Start reordenar
(def reordenar-sql
  "SELECT 
   id,
   p_nombre,
   n_parte,
   p_etiqueta,
   inv_inicio,
   inv_recibido,
   inv_enviado,
   inv_en_mano,
   r_minimo
   FROM productos
   WHERE inv_en_mano < r_minimo
   ORDER BY p_etiqueta")

(defn reordenar [request]
  (let [title "Re-ordenar"
        rows (Query db reordenar-sql)
        ok (get-session-id)
        js nil
        content (reordenar-view title rows)]
    (application title ok js content)))
;; End reordenar