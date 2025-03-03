# Cama utils

## Local setup
W celu uruchomienia środowiska na dockerze należy zbudować obrazy dockerowe poszczególnych aplikacji.
1. W katalogu cama-otp-service wykonać polecenie:
   docker container rm cama-otp-service
   docker rmi cama-otp-service:latest
   docker build -t cama-otp-service:latest .
2. W katalogu cama-api wykonać polecenie:
   docker container rm cama-api
   docker rmi cama-api:latest
   docker build -t cama-api:latest .
3. W katalogu utils uruchomić środowisko dockerowe dla Cama:
    docker-compose up -d
4. Zaimportować kolekcję requestów do Postmana, lub wykorzystać inne narzędzie jak np curl do sprawdzenia czy aplikacje poprawnie działają.

5. Zatrzymanie kontenerów
    docker-compose down

Restart kontenerów z czyszczeniem danych:
docker-compose down --volumes
docker-compose up --build -d
docker-compose logs --follow

## Requests collection
W pliku Cama_api.json załączona jest przykładowa konfiguracja wywołań do Cama Be, która może zostać wykorzystana do testów lokalnych z postawionym środowiskiem na dockerze.
W pliku Cama_api_service.json załączona jest przykładowa konfiguracja wywołań do OTP Mock, która może zostać wykorzystana do testów lokalnych z postawionym środowiskiem na dockerze.
