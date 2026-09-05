# ms-campuslab-bff

Backend for Frontend de CampusLab.

## Tecnologías

- Java
- Spring Boot
- Spring Security
- OAuth2 Resource Server
- JWT

## Funcionalidades

- Recibir solicitudes provenientes de AWS API Gateway.
- Validar el JWT de Azure AD.
- Aplicar autorización por rol.
- Propagar solicitudes hacia los microservicios de dominio.
- Centralizar la comunicación entre frontend y backend.

## Rutas

```http
/api/bookings/**
/api/catalog/**
/api/report/**
/api/audit/**
```

## Roles

- ADMIN
- OPERATOR
- CLIENT
- AUDITOR

## Flujo

```text
AWS API Gateway
↓
ms-campuslab-bff
↓
Microservicio de dominio
```

## Servicios internos

```env
BOOKINGS_SERVICE_URL=
CATALOG_SERVICE_URL=
REPORT_SERVICE_URL=
AUDIT_SERVICE_URL=
```

## Seguridad

```env
AZURE_TENANT_ID=
AZURE_API_CLIENT_ID=
AZURE_ISSUER_URI=https://login.microsoftonline.com/<TENANT_ID>/v2.0
```

## Ejecución local

```bash
./mvnw spring-boot:run
```

## Build

```bash
./mvnw clean package
```
