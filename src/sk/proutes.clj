(ns sk.proutes
  (:require [compojure.core :refer [defroutes GET POST]]
            [sk.proutes.inventario :as inventario]
            [sk.proutes.compras :as compras]
            [sk.proutes.ordenes :as ordenes]
            [sk.proutes.reportes :as reportes]))

(defroutes proutes
  ;; Start inventario
  (GET "/inventario" request [] (inventario/inventario request))
  (POST "/inventario/json/grid" request [] (inventario/grid-json request))
  (GET "/inventario/detalles/:producto_id" [producto_id] (inventario/detalles producto_id))
  (GET "/inventario/json/form/:producto_id" [producto_id] (inventario/form-productos producto_id))
  (POST "/inventario/save" request [] (inventario/productos-save request))
  (POST "/inventario/delete" request [] (inventario/productos-delete request))
  ;; End inventario
  ;; Start compras
  (GET "/compras" request [] (compras/compras request))
  (POST "/compras/json/grid" request [] (compras/grid-compras request))
  (GET "/compras/json/form/:compras_id" [compras_id] (compras/form-compras compras_id))
  (POST "/compras/save" request [] (compras/compras-save request))
  (POST "/compras/delete" request [] (compras/compras-delete request))
  ;; End compras
  ;; Start ordenes
  (GET "/ordenes" request [] (ordenes/ordenes request))
  (POST "/ordenes/json/grid" request [] (ordenes/grid-ordenes request))
  (GET "/ordenes/json/form/:ordenes_id" [ordenes_id] (ordenes/form-ordenes ordenes_id))
  (POST "/ordenes/save" request [] (ordenes/ordenes-save request))
  (POST "/ordenes/delete" request [] (ordenes/ordenes-delete request))
  ;; End ordenes
  ;; Start reportes
  (GET "/reportes" request [] (reportes/reportes request))
  ;; End reportes
  )