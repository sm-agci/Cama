# Cama otp service mock

Budowanie projektu
mvn clean install

Budowanie obrazu dockerowego
docker build -t cama-otp-service:latest .

Podczas uruchomienia należy w propertisach aplikacji ustawić api.token= na wartość którą będzie się przedstawiał serwis