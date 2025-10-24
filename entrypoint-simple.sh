#!/bin/sh
set -e

echo "=== INICIANDO APLICA??O REUSEMI (SIMPLIFICADO) ==="

# Aguardar MySQL usando m?todo nativo do Java/Spring
echo "Aguardando MySQL ficar pronto (m?todo Spring Boot)..."

# Aguardar tempo suficiente para o MySQL inicializar
sleep 45

echo "? Iniciando aplica??o Spring Boot..."
exec java -jar /app/app.jar
