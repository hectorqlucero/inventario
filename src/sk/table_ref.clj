(ns sk.table_ref
  (:require [sk.models.crud :refer [db Query]]
            [sk.models.util :refer [zpl parse-int current_year get-session-id]]
            [selmer.parser :refer [render-file]]
            [clojure.java.io :as io]
            [ondrs.barcode :refer :all]
            [compojure.core :refer [defroutes GET]])
  (:import (net.sourceforge.barbecue Barcode)))

;; start barcode
(def temp-dir
  (let [dir (str (System/getProperty "java.io.tmpdir") "/barcodes/")]
    (.mkdir (io/file dir))
    dir))

(def o-stream (io/output-stream (str temp-dir "/stream")))

(defn create-html [file]
  (str "<img src='" file "'>"))

(defn generate-barcode [id]
  (let [barcode (generate :code128 id)]
    (doto barcode
      (.setBarHeight 50)
      (.setBarWidth 2))
    barcode))

(defn create-barcode [id]
  (let [id (zpl id 8) 
        barcode (generate-barcode id)
        result (write! barcode (str temp-dir id ".jpg"))]
    (str "/uploads/" id ".jpg")))

(defn get-barcodes []
  (let [rows (Query db ["SELECT id,p_etiqueta FROM productos ORDER BY id"])
        result (map #(assoc % :id (create-barcode (:id %))) rows)
        ;;result (into {} (map #(:etiqueta (:p_etiqueta %) (create-barcode (:id %))) rows))
        ]
    (render-file "sk/routes/barcodes/index.html" {:title "Codigo de barras"
                                                  :ok (get-session-id)
                                                  :data result})))
;; End barcode

;; Start get_users
(def get_users-sql
  "SELECT id AS value, concat(firstname,' ',lastname) AS text FROM users order by firstname,lastname")

(defn get-users []
  "Regresa todos los usuarios o vacio ex: (get-users)"
  (Query db [get_users-sql]))
;; End get_users

;; Start get-users-email
(def get-users-email-sql
  "SELECT
   email
   FROM users
   WHERE email = ?")

(defn get-users-email [email]
  "Regresa el correo del usuario o nulo"
  (first (Query db [get-users-email-sql email])))
;; End get-users-email

(defn months []
  "Regresa un arreglo de meses en español ex: (months)"
  (list
   {:value 1 :text "Enero"}
   {:value 2 :text "Febrero"}
   {:value 3 :text "Marzo"}
   {:value 4 :text "Abril"}
   {:value 5 :text "Mayo"}
   {:value 6 :text "Junio"}
   {:value 7 :text "Julio"}
   {:value 8 :text "Agosto"}
   {:value 9 :text "Septiembre"}
   {:value 10 :text "Octubre"}
   {:value 11 :text "Noviembre"}
   {:value 12 :text "Diciembre"}))

(defn years [p n]
  "Genera listado para dropdown dependiendo de p=anterioriores de este año, n=despues de este año,
   ex: (years 5 4)"
  (let [year   (parse-int (current_year))
        pyears (for [n (range (parse-int p) 0 -1)] {:value (- year n) :text (- year n)})
        nyears (for [n (range 0 (+ (parse-int n) 1))] {:value (+ year n) :text (+ year n)})
        years  (concat pyears nyears)]
    years))

;; Start get-productos
(def get-productos-sql
  "SELECT
   id AS value,
   CONCAT(id,' | ',p_etiqueta) as text
   FROM productos
   ORDER BY p_etiqueta")

(defn get-productos []
  "Regresa todos los productos"
  (Query db get-productos-sql))
;; End get-productos

;; Start get-provedores
(def get-provedores-sql
  "SELECT
   id AS value,
   provedor as text
   FROM provedores
   ORDER BY provedor")

(defn get-provedores []
  "Regresa todos los provedores"
  (Query db get-provedores-sql))
;; End get-provedores
