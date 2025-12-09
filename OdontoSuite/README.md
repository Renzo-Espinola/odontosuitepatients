# 🦷 OdontoSuite Patients

**OdontoSuite Patients** es el microservicio encargado de gestionar la
información de pacientes y su historia clínica dentro de la plataforma
OdontoSuite.

Incluye:

-   ABM de pacientes\
-   Historia clínica (Clinical Notes)\
-   Subida y descarga de fotos de pacientes\
-   Arquitectura escalable lista para producción\
-   Storage desacoplado (filesystem hoy, S3/MinIO mañana)\
-   PMD + Spotless para calidad de código

------------------------------------------------------------------------

## 🚀 Tecnologías utilizadas

-   Java 25\
-   Spring Boot 3.5\
-   Spring Web\
-   Spring Data JPA\
-   PostgreSQL\
-   Lombok\
-   Spotless\
-   PMD

------------------------------------------------------------------------

## 📁 Estructura del proyecto

    src/main/java/com/odontosuitepatients
    ├── OdontoSuitePatientsApplication.java
    ├── domain/
    │   ├── model/
    │   │   ├── Patient.java
    │   │   └── ClinicalNote.java
    │   └── repository/
    │       ├── PatientRepository.java
    │       └── ClinicalNoteRepository.java
    ├── application/
    │   ├── dto/
    │   │   ├── PatientRequest.java
    │   │   ├── PatientResponse.java
    │   │   ├── ClinicalNoteRequest.java
    │   │   └── ClinicalNoteResponse.java
    │   ├── mapper/
    │   │   └── PatientMapper.java
    │   └── service/
    │       ├── PatientService.java
    │       └── PatientServiceImpl.java
    ├── storage/
    │   ├── StorageService.java
    │   └── FileSystemStorageService.java
    └── web/
        └── controller/
            └── PatientController.java

------------------------------------------------------------------------

## 🗄️ Base de datos

``` sql
CREATE DATABASE odontosuite_patients;
```

------------------------------------------------------------------------

## ⚙️ Configuración (`application.yml`)

``` yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/odontosuite_patients
    username: postgres
    password: postgres

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true

storage:
  location: uploads/patients

server:
  port: 8081
```

------------------------------------------------------------------------

## 🧩 Endpoints principales

### 📌 Pacientes

  Método   Endpoint                         Descripción
  -------- -------------------------------- ---------------------
  POST     /api/patients                    Crear paciente
  GET      /api/patients                    Listar pacientes
  GET      /api/patients/{id}               Obtener paciente
  PUT      /api/patients/{id}               Actualizar paciente
  DELETE   /api/patients/{id}               Baja lógica
  POST     /api/patients/{id}/photo         Subir foto
  GET      /api/patients/photo/{filename}   Descargar foto

------------------------------------------------------------------------

## 🖼 Subida de imágenes

    POST /api/patients/{id}/photo

Las imágenes se guardan en:

    uploads/patients/patient_<id>_<filename>

Y el campo `photoUrl` guarda el nombre del archivo.

------------------------------------------------------------------------

## 📦 Calidad de código

### Spotless

    ./gradlew spotlessApply

### PMD

    ./gradlew pmdMain

------------------------------------------------------------------------

## ▶️ Cómo correr la aplicación

    ./gradlew clean build
    ./gradlew bootRun

------------------------------------------------------------------------

## 🌱 Preparado para producción

El storage está desacoplado mediante la interfaz `StorageService`, lo
que permite cambiar de filesystem → S3/MinIO sin afectar el resto del
código.

------------------------------------------------------------------------

## 📜 Licencia

Proyecto personal de Renzo Espínola.
