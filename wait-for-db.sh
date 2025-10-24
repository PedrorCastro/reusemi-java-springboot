#!/bin/sh
host="$1"
shift

echo "Aguardando MySQL em $host..."
until nc -z "$host" 3307; do
  echo "MySQL n?o est? dispon?vel - aguardando..."
  sleep 2
done

echo "MySQL est? dispon?vel! Iniciando aplica??o..."
cd /app && java -Dfile.encoding=UTF-8 -Dspring.profiles.active=docker -jar app.jar
