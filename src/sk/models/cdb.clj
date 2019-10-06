(ns sk.models.cdb
  (:require [sk.models.crud :refer :all]
            [noir.util.crypt :as crypt]))

(def users-sql
  "CREATE TABLE users (
  id int(11) NOT NULL AUTO_INCREMENT,
  lastname varchar(45) DEFAULT NULL,
  firstname varchar(45) DEFAULT NULL,
  username varchar(45) DEFAULT NULL,
  password TEXT DEFAULT NULL,
  dob varchar(45) DEFAULT NULL,
  cell varchar(45) DEFAULT NULL,
  phone varchar(45) DEFAULT NULL,fax varchar(45) DEFAULT NULL,
  email varchar(100) DEFAULT NULL,
  level char(1) DEFAULT NULL COMMENT 'A=Administrador,U=Usuario,S=Sistema',
  active char(1) DEFAULT NULL COMMENT 'T=Active,F=Not active',
  PRIMARY KEY (id)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8")

(def user-rows
  [{:lastname  "Lucero"
    :firstname "Hector"
    :username  "hectorqlucero@gmail.com"
    :password  (crypt/encrypt "elmo1200")
    :dob       "1957-02-07"
    :email     "hectorqlucero@gmail.com"
    :level     "S"
    :active    "T"}
   {:lastname  "Pescador"
    :firstname "Marco"
    :username  "marcopescador@hotmail.com"
    :dob       "1968-10-04"
    :email     "marcopescador@hotmail.com"
    :password  (crypt/encrypt "10201117")
    :level     "S"
    :active    "T"}])

(def orders-sql
  "CREATE TABLE orders (
   id int(11) unsigned NOT NULL AUTO_INCREMENT,
   titulo varchar(255) DEFAULT NULL,
   nombre varchar(255) DEFAULT NULL,
   apell_paterno varchar(255) DEFAULT NULL,
   apell_materno varchar(255) DEFAULT NULL,
   producto_id int(11) unsigned NOT NULL,
   enviado_numero int(11) DEFAULT NULL,
   orden_fecha date DEFAULT NULL,
   PRIMARY KEY(id),
   KEY order_product_fk (producto_id),
   CONSTRAINT order_product_fk FOREIGN KEY (producto_id) REFERENCES productos (id)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;")

(def productos-sql
  "CREATE TABLE productos (
   id int(11) unsigned NOT NULL AUTO_INCREMENT,
   p_nombre varchar(255) DEFAULT NULL,
   n_parte varchar(255) DEFAULT NULL,
   p_etiqueta varchar(255) DEFAULT NULL,
   inv_inicio int(11) DEFAULT NULL,
   inv_recibido int(11) DEFAULT NULL,
   inv_enviado int(11) DEFAULT NULL,
   inv_en_mano int(11) DEFAULT NULL,
   r_minimo int(11) DEFAULT NULL,
   PRIMARY KEY(id)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;")

(def compras-sql
  "CREATE TABLE compras (
   id int(11) unsigned NOT NULL AUTO_INCREMENT,
   provedor_id int(11) unsigned NOT NULL,
   producto_id int(11) unsigned NOT NULL,
   num_recibido int(11) NOT NULL,
   compra_fecha date NOT NULL,
   PRIMARY KEY (id),
   KEY compra_producto_fk (producto_id),
   KEY compra_provedor_fk (provedor_id),
   CONSTRAINT compra_producto_fk FOREIGN KEY (producto_id) REFERENCES productos (id),
   CONSTRAINT compra_provedor_fk FOREIGN KEY (provedor_id) REFERENCES provedores (id)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;")

(def provedores-sql
  "CREATE TABLE provedores (
   id int(11) unsigned NOT NULL AUTO_INCREMENT,
   provedor varchar(255) NOT NULL DEFAULT '',
   PRIMARY KEY (id)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;")

(defn create-database []
  "Creates database and a default admin users"
  (Query! db users-sql)
  (Insert-multi db :users user-rows)
  (Query! db productos-sql)
  (Query! db orders-sql)
  (Query! db provedores-sql)
  (Query! db compras-sql))

(defn reset-database []
  "Removes existing tables and re-creates them"
  (Query! db "DROP table IF EXISTS users")
  (Query! db users-sql)
  (Insert-multi db :users user-rows)
  (Query! db "DROP table IF EXISTS compras")
  (Query! db "DROP table IF EXISTS provedores")
  (Query! db "DROP table IF EXISTS orders")
  (Query! db "DROP table IF EXISTS productos")
  (Query! db productos-sql)
  (Query! db orders-sql)
  (Query! db provedores-sql)
  (Query! db compras-sql))

;; Start migrate
(def productos-rows
  [{:id 1
    :p_nombre "Servidor Dell"
    :n_parte "XP 2000"
    :p_etiqueta "Servidor Dell- XP 2000"
    :inv_inicio 100017
    :inv_recibido 35
    :inv_enviado 25
    :inv_en_mano 100017
    :r_minimo 15}
   {:id 2
    :p_nombre "Google Chromebooks"
    :n_parte "1"
    :p_etiqueta "Google Chromebooks- 1.0"
    :inv_inicio 100
    :inv_recibido 75
    :inv_enviado 654
    :inv_en_mano -479
    :r_minimo 20}
   {:id 3
    :p_nombre "Cisco Ruteador"
    :n_parte "10X"
    :p_etiqueta "Cisco Ruteador- 10X"
    :inv_inicio 45
    :inv_recibido 143
    :inv_enviado 76
    :inv_en_mano 86
    :r_minimo 88}
   {:id 4
    :p_nombre "sadasd"
    :n_parte "21"
    :p_etiqueta "sadasd- 21"
    :inv_inicio 25
    :inv_recibido 0
    :inv_enviado 0
    :inv_en_mano 25
    :r_minimo 10}
   {:id 5
    :p_nombre "semih"
    :n_parte "37"
    :p_etiqueta "semih- 37"
    :inv_inicio 1
    :inv_recibido 12
    :inv_enviado 5
    :inv_en_mano 8
    :r_minimo 5}
   {:id 6
    :p_nombre "Crazy Horse Ruteador"
    :n_parte "123DF5"
    :p_etiqueta "Crazy Horse Ruteador- 123DF5"
    :inv_inicio 5
    :inv_recibido 23
    :inv_enviado 0
    :inv_en_mano 28
    :r_minimo 1}
   {:id 7
    :p_nombre "Monitores"
    :n_parte ""
    :p_etiqueta "Monitores- 999"
    :inv_inicio 0
    :inv_recibido 0
    :inv_enviado 0
    :inv_en_mano 0
    :r_minimo 0}
   {:id 8
    :p_nombre "PRUEBA"
    :n_parte "123"
    :p_etiqueta "PRUEBA- 123"
    :inv_inicio 10
    :inv_recibido 0
    :inv_enviado 0
    :inv_en_mano 10
    :r_minimo 10}
   {:id 9
    :p_nombre "bob"
    :n_parte "bob-1"
    :p_etiqueta "bob- 1"
    :inv_inicio 500
    :inv_recibido 412
    :inv_enviado 267
    :inv_en_mano 6450
    :r_minimo 400}
   {:id 10
    :p_nombre "Multimeter"
    :n_parte "c345"
    :p_etiqueta "Multimeter- c345"
    :inv_inicio 3
    :inv_recibido 28
    :inv_enviado 29
    :inv_en_mano 2
    :r_minimo 4}
   {:id 11
    :p_nombre "dfgdf"
    :n_parte "54334"
    :p_etiqueta "dfgdf- 54334"
    :inv_inicio 0
    :inv_recibido 0
    :inv_enviado 300
    :inv_en_mano -300
    :r_minimo 0}
   {:id 12
    :p_nombre "UniBox"
    :n_parte "1"
    :p_etiqueta "UniBox- 1"
    :inv_inicio 200
    :inv_recibido 0
    :inv_enviado 0
    :inv_en_mano 200
    :r_minimo 300}
   {:id 13
    :p_nombre "Test 1"
    :n_parte "123456"
    :p_etiqueta "Test- 123456"
    :inv_inicio 50
    :inv_recibido 1234
    :inv_enviado 0
    :inv_en_mano 1284
    :r_minimo 10}
   {:id 14
    :p_nombre "Toby"
    :n_parte "57456"
    :p_etiqueta "Toby- 57456"
    :inv_inicio 567
    :inv_recibido 22
    :inv_enviado 12
    :inv_en_mano 577
    :r_minimo 56467}
   {:id 15
    :p_nombre "sdsad"
    :n_parte "sdsdsad"
    :p_etiqueta "sdsad- sdsdsad"
    :inv_inicio 12
    :inv_recibido 0
    :inv_enviado 0
    :inv_en_mano 12
    :r_minimo 12}
   {:id 16
    :p_nombre "test"
    :n_parte "55555"
    :p_etiqueta "test- 55555"
    :inv_inicio 500
    :inv_recibido 12
    :inv_enviado 0
    :inv_en_mano 512
    :r_minimo 25}
   {:id 17
    :p_nombre "Firewalls"
    :n_parte "362436"
    :p_etiqueta "Firewalls- 362436"
    :inv_inicio 5
    :inv_recibido 33
    :inv_enviado 900
    :inv_en_mano -862
    :r_minimo 10}
   {:id 18
    :p_nombre "Cables"
    :n_parte "7734"
    :p_etiqueta "Cables- 7734"
    :inv_inicio 9
    :inv_recibido 475
    :inv_enviado 9000
    :inv_en_mano 16
    :r_minimo 100}
   {:id 19
    :p_nombre "Test"
    :n_parte "1"
    :p_etiqueta "Test- 001"
    :inv_inicio 25
    :inv_recibido 0
    :inv_enviado 0
    :inv_en_mano 25
    :r_minimo 222}])

(def orders-rows
  [{:id 1
    :nombre "Suzy"
    :apell_paterno "Cliente"
    :producto_id 3
    :enviado_numero 10
    :orden_fecha "2014-04-01"}
   {:id 2
    :nombre "Suzy"
    :apell_paterno "Cliente"
    :producto_id 3
    :enviado_numero 20
    :orden_fecha "2014-04-22"}
   {:id 3
    :nombre "Ben"
    :apell_paterno "Thomas"
    :producto_id 1
    :enviado_numero 5
    :orden_fecha "2014-04-11"}
   {:id 4
    :nombre "Johnny"
    :apell_paterno "Test"
    :producto_id 3
    :enviado_numero 10
    :orden_fecha "2014-04-02"}
   {:id 5
    :nombre "Steve"
    :apell_paterno "Smith"
    :producto_id 1
    :enviado_numero 20
    :orden_fecha "2014-04-15"}
   {:id 6
    :nombre "Steve"
    :apell_paterno "Palmer"
    :producto_id 3
    :enviado_numero 3
    :orden_fecha "2014-02-22"}
   {:id 7
    :nombre "Tim"
    :apell_paterno "Scott"
    :producto_id 3
    :enviado_numero 5
    :orden_fecha "2014-03-22"}
   {:id 8
    :nombre "Dave"
    :apell_paterno "Boyd"
    :producto_id 3
    :enviado_numero 10
    :orden_fecha "2014-01-22"}
   {:id 9
    :nombre "Suzy"
    :apell_paterno "Cliente"
    :producto_id 2
    :enviado_numero 30
    :orden_fecha "2014-01-21"}
   {:id 10
    :nombre "Dylan"
    :apell_paterno "Test"
    :producto_id 3
    :enviado_numero 5
    :orden_fecha "2014-04-23"}
   {:id 11
    :nombre "Betty"
    :apell_paterno "Fryar"
    :producto_id 3
    :enviado_numero 12
    :orden_fecha "2014-04-22"}
   {:id 12
    :nombre "Jerry"
    :apell_paterno "Sellers"
    :producto_id 2
    :enviado_numero 124
    :orden_fecha "2014-04-22"}
   {:id 13
    :nombre "BOB"
    :apell_paterno "SMITH"
    :producto_id 2
    :enviado_numero 500
    :orden_fecha "2014-05-11"}
   {:id 14
    :nombre "Suzy"
    :apell_paterno "Cliente"
    :producto_id 5
    :enviado_numero 5
    :orden_fecha "2015-04-07"}
   {:id 15
    :nombre "Suzy"
    :apell_paterno "Cliente"
    :producto_id 9
    :enviado_numero 50
    :orden_fecha "2015-04-07"}
   {:id 16
    :nombre "Suzy"
    :apell_paterno "Cliente"
    :producto_id 3
    :enviado_numero 1
    :orden_fecha "2015-04-07"}
   {:id 17
    :nombre "Suzy"
    :apell_paterno "Cliente"
    :producto_id 10
    :enviado_numero 5
    :orden_fecha "2015-09-09"}
   {:id 18
    :nombre "John"
    :apell_paterno "Lemeasure"
    :producto_id 10
    :enviado_numero 12
    :orden_fecha "2016-02-05"}
   {:id 19
    :nombre "Suzy"
    :apell_paterno "Cliente"
    :producto_id 9
    :enviado_numero 2
    :orden_fecha "2017-02-25"}
   {:id 20
    :nombre ""
    :apell_paterno ""
    :producto_id 9
    :enviado_numero 1
    :orden_fecha "2017-01-15"}
   {:id 21
    :nombre "llkjh"
    :apell_paterno "kjlkh"
    :producto_id 11
    :enviado_numero 250
    :orden_fecha "2017-02-15"}
   {:id 22
    :nombre "Suzy"
    :apell_paterno "Cliente"
    :producto_id 16
    :enviado_numero 14
    :orden_fecha "2017-04-05"}
   {:id 23
    :nombre "Suzy"
    :apell_paterno "Cliente"
    :producto_id 11
    :enviado_numero 50
    :orden_fecha "2017-06-05"}
   {:id 24
    :nombre "Suzy"
    :apell_paterno "Cliente"
    :producto_id 9
    :enviado_numero 200
    :orden_fecha "2017-06-05"}
   {:id 25
    :nombre "Test"
    :apell_paterno "Cowley"
    :producto_id 14
    :enviado_numero 12
    :orden_fecha "2017-11-05"}
   {:id 26
    :nombre "Elvis"
    :apell_paterno "P"
    :producto_id 17
    :enviado_numero 900
    :orden_fecha "2017-04-05"}
   {:id 27
    :nombre "Elvis"
    :apell_paterno "P"
    :producto_id 18
    :enviado_numero 9000
    :orden_fecha "2017-06-05"}
   {:id 28
    :nombre ""
    :apell_paterno ""
    :producto_id 4
    :enviado_numero 0
    :orden_fecha "2017-04-05"}])

(def compras-rows
  [{:id 1
    :provedor_id 2
    :producto_id 2
    :num_recibido 50
    :compra_fecha "2014-11-02"}
   {:id 2
    :provedor_id 2
    :producto_id 1
    :num_recibido 15
    :compra_fecha "2014-09-02"}
   {:id 3
    :provedor_id 3
    :producto_id 3
    :num_recibido 10
    :compra_fecha "2014-11-12"}
   {:id 4
    :provedor_id 2
    :producto_id 2
    :num_recibido 25
    :compra_fecha "2014-01-02"}
   {:id 5
    :provedor_id 2
    :producto_id 3
    :num_recibido 20
    :compra_fecha "2014-02-22"}
   {:id 6
    :provedor_id 1
    :producto_id 1
    :num_recibido 5
    :compra_fecha "2015-11-02"}
   {:id 7
    :provedor_id 3
    :producto_id 3
    :num_recibido 3
    :compra_fecha "2014-01-02"}
   {:id 8
    :provedor_id 1
    :producto_id 3
    :num_recibido 20
    :compra_fecha "2015-11-11"}
   {:id 9
    :provedor_id 2
    :producto_id 1
    :num_recibido 0
    :compra_fecha "2014-11-02"}
   {:id 10
    :provedor_id 1
    :producto_id 1
    :num_recibido 5
    :compra_fecha "2016-11-02"}
   {:id 11
    :provedor_id 2
    :producto_id 5
    :num_recibido 12
    :compra_fecha "2016-11-02"}
   {:id 12
    :provedor_id 2
    :producto_id 3
    :num_recibido 90
    :compra_fecha "2016-11-02"}
   {:id 13
    :provedor_id 1
    :producto_id 6
    :num_recibido 23
    :compra_fecha "2016-08-02"}
   {:id 14
    :provedor_id 2
    :producto_id 10
    :num_recibido 25
    :compra_fecha "2017-11-02"}
   {:id 15
    :provedor_id 2
    :producto_id 10
    :num_recibido 13
    :compra_fecha "2017-11-02"}
   {:id 16
    :provedor_id 1
    :producto_id 10
    :num_recibido 0
    :compra_fecha "2017-01-02"}
   {:id 17
    :provedor_id 1
    :producto_id 2
    :num_recibido 0
    :compra_fecha "2017-02-22"}
   {:id 18
    :provedor_id 2
    :producto_id 1
    :num_recibido 10
    :compra_fecha "2017-03-02"}
   {:id 19
    :provedor_id 2
    :producto_id 9
    :num_recibido 12
    :compra_fecha "2017-03-03"}
   {:id 20
    :provedor_id 2
    :producto_id 13
    :num_recibido 1234
    :compra_fecha "2017-05-12"}
    {:id 21
    :provedor_id 1
    :producto_id 12
    :num_recibido 0
    :compra_fecha "2017-05-22"}
    {:id 22
    :provedor_id 1
    :producto_id 13
    :num_recibido 0
    :compra_fecha "2017-06-12"}
    {:id 23
    :provedor_id 2
    :producto_id 3
    :num_recibido 0
    :compra_fecha "2017-08-02"}
    {:id 24
    :provedor_id 3
    :producto_id 9
    :num_recibido 400
    :compra_fecha "2017-10-02"}
    {:id 25
    :provedor_id 1
    :producto_id 14
    :num_recibido 0
    :compra_fecha "2017-11-02"}
    {:id 26
    :provedor_id 2
    :producto_id 16
    :num_recibido 12
    :compra_fecha "2017-11-30"}
    {:id 27
    :provedor_id 1
    :producto_id 3
    :num_recibido 0
    :compra_fecha "2017-07-02"}
    {:id 28
    :provedor_id 3
    :producto_id 17
    :num_recibido 33
    :compra_fecha "2017-07-12"}
    {:id 29
    :provedor_id 1
    :producto_id 18
    :num_recibido 453
    :compra_fecha "2017-07-23"}
    {:id 30
    :provedor_id 2
    :producto_id 18
    :num_recibido 22
    :compra_fecha "2017-11-02"}])

(def provedores-rows
  [{:id 1
    :provedor "ShockWave Tech"}
   {:id 2
    :provedor "CDW"}
   {:id 3
    :provedor "ACME Tech Supplies"}])

(defn migrate []
  "Migrate by the seat of my pants"
  (Query! db "DROP table IF EXISTS users")
  (Query! db users-sql)
  (Insert-multi db :users user-rows)
  (Query! db "DROP table IF EXISTS compras")
  (Query! db "DROP table IF EXISTS provedores")
  (Query! db "DROP table IF EXISTS orders")
  (Query! db "DROP table IF EXISTS productos")
  (Query! db productos-sql)
  (Insert-multi db :productos productos-rows)
  (Query! db orders-sql)
  (Insert-multi db :orders orders-rows)
  (Query! db provedores-sql)
  (Insert-multi db :provedores provedores-rows)
  (Query! db compras-sql)
  (Insert-multi db :compras compras-rows))
;; END migrate

;;(create-database)
