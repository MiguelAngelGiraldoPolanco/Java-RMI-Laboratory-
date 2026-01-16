# Java RMI Laboratory - Red Social "Trino"

Este proyecto es una implementación de una red social simplificada basada en una arquitectura distribuida de tres capas utilizando **Java RMI (Remote Method Invocation)**. El sistema permite la comunicación asíncrona entre usuarios mediante el mecanismo de **callbacks**.

## 🚀 Estructura del Proyecto

El sistema se divide en tres componentes principales:

1.  **Capa de Persistencia (BD):** Gestiona el almacenamiento de usuarios, trinos y relaciones de seguimiento.
2.  **Capa de Servidor (Gestor):** Contiene la lógica de negocio y gestiona las notificaciones en tiempo real.
3.  **Capa de Cliente:** Interfaz de consola para que los usuarios interactúen con el sistema.

## 📋 Requisitos

* Java JDK 8 o superior.
* Entorno Windows (para ejecutar los archivos `.bat`).

## 🛠️ Instalación y Ejecución

Para poner en marcha el laboratorio, sigue este orden exacto utilizando los scripts incluidos:

1.  **Lanzar la Base de Datos:**
    Ejecuta `01_Lanzar_BD.bat`. Esto iniciará el registro RMI y el servicio de persistencia.
    
2.  **Lanzar el Servidor:**
    Ejecuta `02_Lanzar_Servidor.bat`. El servidor se conectará a la BD y quedará a la espera de clientes.
    
3.  **Lanzar el Cliente:**
    Ejecuta `03_Lanzar_Cliente.bat`. Puedes abrir varias instancias para probar la comunicación entre diferentes usuarios.

## ✨ Funcionalidades Principales

* **Registro y Login:** Autenticación de usuarios contra la capa de persistencia.
* **Publicación de Trinos:** Envío de mensajes cortos a la red.
* **Sistema de Seguimiento:** Capacidad de seguir y dejar de seguir a otros usuarios.
* **Callbacks en tiempo real:** Recepción instantánea de mensajes de usuarios seguidos sin necesidad de refrescar.

## 📂 Contenido del Repositorio

* `src/`: Código fuente Java organizado por paquetes.
* `*.jar`: Ejecutables compilados de cada capa.
* `*.bat`: Scripts de automatización para el despliegue.
* `Miguel_Angel_Giraldo_Polanco.iml`: Archivo de configuración del proyecto para IntelliJ IDEA.
