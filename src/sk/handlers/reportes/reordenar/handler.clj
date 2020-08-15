(ns sk.handlers.reportes.reordenar.handler
  (:require [sk.models.crud :refer [db Query]]
            [sk.models.util :refer [get-session-id]]
            [sk.layout :refer [application]]
            [sk.handlers.reportes.reordenar.view :refer [reordenar-view]]))

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

(defn reordenar [_]
  (let [title "Re-ordenar"
        rows (Query db reordenar-sql)
        ok (get-session-id)
        js nil
        content (reordenar-view title rows)]
    (application title ok js content)))
;; End reordenar
