(ns sk.proutes.reportes
  (:require [cheshire.core :refer [generate-string]]
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

(defn inventario [request]
  (render-file "404.html" {:error "En construccion!"
                           :return-url "/"}))

(defn recibido [request]
  (render-file "404.html" {:error "En construccion!"
                           :return-url "/"}))

(defn enviado [request]
  (render-file "404.html" {:error "En construccion!"
                           :return-url "/"}))

(defn reordenar [request]
  (render-file "404.html" {:error "En construccion!"
                           :return-url "/"}))
