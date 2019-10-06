# inventario
Pequeño systema de inventario en Clojure

## Pre-requisitos
1. Leiningen 2.0.0 o mas nuevo
2. jdk8 o mas nuevo
3. MySQL o MariaDB

## Configurar
1. Crear una base de datos "inventario" en MySQL o MariaDB
2. Clonar el repositorio
3. Crear un directorio para configuracion /inventario/resources/private
4. Crear un archivo en /inventario/resources/private/config.clj
   Exemplo de /inventario/resources/private/config.clj
    ```
    {:db-protocol "mysql"
    :db-name "//localhost:3306/inventario?characterEncoding=UTF-8"
    :db-user "xxxxxxx"
    :db-pwd "xxxxxxxxxx"
    :db-class "com.mysql.cj.jdbc.Driver"
    :email-host "xxxxxxxxxx"
    :email-user "xxxxxxxxxx"
    :email-pwd "xxxxxxxxxx"
    :port 8080
    :tz "US/Pacific"
    :site-name "Inventario"
    :base-url "http://0.0.0.0:8080/"
    :uploads "./uploads"
    :path "/uploads/"}
    ```
  Remplazar las "xxxx" con datos que apliquen a su contorno
5. Usar este archivo /inventario/sql/inventario.sql para crear las tablas y datos de probeta (MySQL)

## Correr la aplicacion
Ir al directorio donde clono la aplicacion ex: cd /Repo/inventario y executar: lein run
