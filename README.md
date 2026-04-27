# infra

Infraestructura local compartida para que los microservicios bancarios core puedan
funcionar en Docker.

Antes de levantar `customer-service`, `credit-service`, `account-service`,
`transaction-service`, `report-service` o `yanki-service`, se debe iniciar este
compose:

```powershell
cd .\infra
docker compose up -d --build
```

Para detener la infraestructura:

```powershell
docker compose down
```

## Proyectos incluidos

- `config-server`: entrega configuracion centralizada a los servicios.
- `eureka-server`: permite que los servicios se registren y se descubran entre si.
- `api-gateway`: punto de entrada HTTP para enrutar peticiones hacia los servicios.
- `auth-service`: gestiona autenticacion y emision de tokens.
- `mongodb`: base de datos compartida en desarrollo; cada microservicio usa su propia base logica.
- `mongo-express`: interfaz web para revisar datos de MongoDB.
- `redis`: almacenamiento temporal y cache usado por algunos servicios.
- `zookeeper`: soporte interno para el broker de eventos.
- `kafka`: broker de eventos para comunicacion asincrona entre servicios.
- `kafka-ui`: interfaz web para revisar topicos y mensajes de eventos.

## Puertos utiles

- API Gateway: `http://localhost:8080`
- Config Server: `http://localhost:8888`
- Eureka UI: `http://localhost:8761`
- Mongo Express: `http://localhost:8085`
- Kafka UI: `http://localhost:9000`
