function stop_environment() {
  echo "Stopping ..."
  docker compose -p mood-tracker -f local/docker-compose.yaml down
}

function start_environment() {
  echo "Starting ..."
  docker compose -p mood-tracker -f local/docker-compose.yaml up -d
}

function package() {
  ./mvnw package -DskipTests
}

function unit_test() {
  ./mvnw test
}

function integration_test() {
  ./mvnw package test -P integration
}

function run() {
  SPRING_PROFILES_ACTIVE=local ./mvnw spring-boot:run
}