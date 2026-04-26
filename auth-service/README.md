# auth-service

Microservicio mock de autenticacion. Valida usuarios definidos en `application.yml`
y genera JWT firmados con el mismo `jwt.secret` que usa `api-gateway`.

## Puerto

```text
http://localhost:8088
```

## Login directo

```powershell
curl -X POST "http://localhost:8088/auth/login" `
  -H "Content-Type: application/json" `
  -d "{\"username\":\"admin\",\"password\":\"admin123\"}"
```

## Login por gateway

```powershell
curl -X POST "http://localhost:8080/auth-service/auth/login" `
  -H "Content-Type: application/json" `
  -d "{\"username\":\"admin\",\"password\":\"admin123\"}"
```

## Usar token contra account-service por gateway

```powershell
curl -X GET "http://localhost:8080/account-service/api/v1/accounts/{accountId}/balance" `
  -H "Authorization: Bearer {accessToken}"
```

## Usuarios mock

```text
admin / admin123
customer / customer123
```
