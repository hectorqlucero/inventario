(ns sk.handlers.reportes.inventario.handler
  (:require [sk.models.crud :refer [db Query]]
            [sk.models.util :refer [get-session-id]]
            [sk.layout :refer [application]]
            [sk.handlers.reportes.inventario.view :refer [inventario-view]]))

;; Start inventario
(def inventario-sql
  "
  SELECT
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
  ORDER BY p_etiqueta
  ")

(defn inventario [_]
  (let [title "Inventario"
        rows (Query db inventario-sql)
        ok (get-session-id)
        content (inventario-view title rows)
        js nil]
    (application title ok js content)))
;; End inventario
