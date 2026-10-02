# IM3 resource-service & song-service

Two Spring Boot services (`resource_service` :8080, `song_service` :8081), each with its own PostgreSQL database.
Tables are created by the SQL scripts in `init-scripts/`, which run inside the DB containers on first start.

## 1. Run only the databases

```sh
docker compose up -d resource-db song-db
```

Databases are published on the host: `resource-db` → `localhost:5434`, `song-db` → `localhost:5433`.
You can then run the services from your IDE/Maven; without env vars they use the `localhost` defaults in `application.properties`.

## 2. Run each service with plain Docker

Start the databases first (step 1), then build and run each service on the compose network (`<folder>_default`, here `im3_default`; check with `docker network ls`).
`--env-file .env` supplies the ports, DB URLs and credentials.

```sh
docker build -t song-service ./song_service
docker run --rm --name song-service --network im3_default --env-file .env \
  -p 8081:8081 song-service

docker build -t resource-service ./resource_service
docker run --rm --name resource-service --network im3_default --env-file .env \
  -e SONG_SERVICE_NAME=song-service \
  -p 8080:8080 resource-service
```

## 3. Run everything with Compose

```sh
docker compose up --build
```

Stop with `docker compose down`.
Data is not persisted, so the databases are recreated (and the init scripts re-run) on every `up`.
