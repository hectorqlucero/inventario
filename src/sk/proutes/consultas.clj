(ns sk.proutes.consultas
  (:require [cheshire.core :refer [generate-string]]
            [clojure.java.io :as io]
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
  (render-file "sk/proutes/consultas/inventario.html" {:title "Inventario"
                                                       :rows (Query db inventario-sql)
                                                       :ok (get-session-id)}))
;; End inventario

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

(defn recibido [request]
  (render-file "sk/proutes/consultas/recibido.html" {:title "Recibido"
                                                     :rows (Query db recibido-sql)
                                                     :ok (get-session-id)}))
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

(defn enviado [request]
  (render-file "sk/proutes/consultas/enviado.html" {:title "Enviado"
                                                    :rows (Query db enviado-sql)
                                                    :ok (get-session-id)}))
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
  (render-file "sk/proutes/consultas/reordenar.html" {:title "Re-ordenar"
                                                      :rows (Query db reordenar-sql)
                                                      :ok (get-session-id)}))
;; End reordenar
