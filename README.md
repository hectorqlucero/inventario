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
4. Copiar el contenido de /inventario/resources/private/config_example.clj a config.clj
5. Remplazar las "xxxxx" con datos que apliquen a su contorno en el archivo config.clj que copio de
   config_example.clj
5. Usar este archivo /inventario/sql/inventario.sql para crear las tablas y datos de probeta (MySQL)

## Correr la aplicacion
Ir al directorio donde clono la aplicacion ex: cd /Repo/inventario y executar: lein run
