(ns sk.handlers.compras.handler
  (:require [cheshire.core :refer [generate-string]]
            [sk.models.crud :refer [build-form-row
                                    build-form-save
                                    build-form-delete]]
            [sk.models.grid :as grid]
            [sk.models.util :refer [get-session-id
                                    parse-int]]
            [sk.layout :refer [application]]
            [sk.handlers.compras.view :refer [compras-view compras-scripts]]))

(defn compras
  [_]
  (try
    (let [title "Compras"
          ok (get-session-id)
          js (compras-scripts)
          content (compras-view title)]
      (application title ok js content))
    (catch Exception e (.getMessage e))))

;; Start compras grid
(def search-columns
  ["compras.id"
   "DATE_FORMAT(compras.compra_fecha,'%m/%d/%Y')"
   "CONCAT(productos.id,' | ',productos.p_etiqueta)"
   "compras.num_recibido"
   "provedores.provedor"])

(def aliases-columns
  ["compras.id"
   "DATE_FORMAT(compras.compra_fecha,'%m/%d/%Y') as compra_fecha"
   "CONCAT(productos.id,' | ',productos.p_etiqueta) as producto_id"
   "compras.num_recibido"
   "provedores.provedor as provedor_id"])

(defn compras-grid [{params :params}]
  (try
    (let [table "compras"
          scolumns (grid/convert-search-columns search-columns)
          aliases aliases-columns
          join (str "join productos on productos.id = compras.producto_id"
                    " "
                    "join provedores on provedores.id = compras.provedor_id")
          search (grid/grid-search (:search params nil) scolumns)
          order (grid/grid-sort (:sort params nil) (:order params nil))
          offset (grid/grid-offset (parse-int (:rows params)) (parse-int (:page params)))
          rows (grid/grid-rows table aliases join search order offset)]
      (generate-string rows))
    (catch Exception e (.getmessage e))))
;; End compras grid

(defn compras-form
  [id]
  (try
    (let [table "compras"]
      (build-form-row table id))
    (catch Exception e (.getMessage e))))

(defn compras-save
  [{params :params}]
  (try
    (let [table "compras"]
      (build-form-save params table))
    (catch Exception e (.getMessage e))))

(defn compras-delete
  [{params :params}]
  (try
    (let [table "compras"]
      (build-form-delete params table))
    (catch Exception e (.getMessage e))))
