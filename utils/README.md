# Cama utils

## Local setup
W celu uruchomienia środowiska na dockerze należy zbudować obrazy dockerowe poszczególnych aplikacji.
1. W katalogu cama-otp-mock wykonać polecenie:
   docker build -t cama-otp-mock:latest .
2. W katalogu cama-be wykonać polecenie:
   docker build -t cama-otp:latest .
3. W katalogu utils uruchomić środowisko dockerowe dla Cama:
    docker-compose up -d
4. Zaimportować kolekcję requestów do Postmana, lub wykorzystać inne narzędzie jak np curl do sprawdzenia czy aplikacje poprawnie działają.

## Requests collection
W pliku Cama_api.json załączona jest przykładowa konfiguracja wywołań do Cama Be, która może zostać wykorzystana do testów lokalnych z postawionym środowiskiem na dockerze.
W pliku Cama_api_mock.json załączona jest przykładowa konfiguracja wywołań do OTP Mock, która może zostać wykorzystana do testów lokalnych z postawionym środowiskiem na dockerze.
