(ns sk.proutes
  (:require [compojure.core :refer [defroutes GET POST]]
            [sk.handlers.admin.users.handler :as users]
            [sk.handlers.inventario.handler :as inventario]
            [sk.handlers.compras.handler :as compras]
            [sk.handlers.ordenes.handler :as ordenes]
            [sk.handlers.reportes.inventario.handler :as r_inventario]
            [sk.handlers.reportes.recibido.handler :as r_recibido]
            [sk.handlers.reportes.enviado.handler :as r_enviado]
            [sk.handlers.reportes.reordenar.handler :as r_reordenar]
            [sk.handlers.barcodes.bar.handler :as barcodes]
            [sk.handlers.barcodes.qr.handler :as qr]))

(defroutes proutes
  ;; Start users
  (GET "/admin/users"  req [] (users/users req))
  (POST "/admin/users" req [] (users/users-grid req))
  (GET "/admin/users/edit/:id" [id] (users/users-form id))
  (POST "/admin/users/save" req [] (users/users-save req))
  (POST "/admin/users/delete" req [] (users/users-delete req))
  ;; End users
  ;; Start inventario
  (GET "/inventario"  req [] (inventario/inventario req))
  (POST "/inventario" req [] (inventario/inventario-grid req))
  (GET "/inventario/detalles/:producto_id" [producto_id] (inventario/detalles producto_id))
  (GET "/inventario/edit/:id" [id] (inventario/inventario-form id))
  (POST "/inventario/save" req [] (inventario/inventario-save req))
  (POST "/inventario/delete" req [] (inventario/inventario-delete req))
  ;; End inventario
  ;; Start compras
  (GET "/compras"  req [] (compras/compras req))
  (POST "/compras" req [] (compras/compras-grid req))
  (GET "/compras/edit/:id" [id] (compras/compras-form id))
  (POST "/compras/save" req [] (compras/compras-save req))
  (POST "/compras/delete" req [] (compras/compras-delete req))
  ;; End compras
  ;; Start ordenes
  (GET "/ordenes"  req [] (ordenes/ordenes req))
  (POST "/ordenes" req [] (ordenes/ordenes-grid req))
  (GET "/ordenes/edit/:id" [id] (ordenes/ordenes-form id))
  (POST "/ordenes/save" req [] (ordenes/ordenes-save req))
  (POST "/ordenes/delete" req [] (ordenes/ordenes-delete req))
  ;; End ordenes
  ;; Start reportes
  (GET "/reportes/inventario" req [] (r_inventario/inventario req))
  (GET "/reportes/recibido" req [] (r_recibido/recibido req))
  (GET "/reportes/enviado" req [] (r_enviado/enviado req))
  (GET "/reportes/reordenar" req [] (r_reordenar/reordenar req))
  ;; End reportes
  ;; Start barcodes
  (GET "/barcodes" req [] (barcodes/get-barcodes req))
  (GET "/qr" req [] (qr/get-barcodes req)))
  ;; End barcodes
  
