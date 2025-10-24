#!/bin/sh
set -e

echo "=== INICIANDO APLICA??O REUSEMI ==="

# Aguardar MySQL ficar dispon?vel na porta
echo "Aguardando MySQL na porta 3306..."
while ! nc -z reusemi-db 3306; do
  sleep 1
done
echo "? MySQL est? aceitando conex?es!"

# AGUARDAR O MYSQL FICAR COMPLETAMENTE PRONTO PARA JDBC
echo "Aguardando MySQL ficar completamente pronto para JDBC..."
sleep 25

# VERIFICAR SE O MYSQL EST? REALMENTE PRONTO testando conex?o JDBC
echo "Verificando se MySQL est? pronto para JDBC..."
while ! mysql -h reusemi-db -P 3306 -u root -pREUSEMI123 -e "SELECT 1" > /dev/null 2>&1; do
  echo "MySQL ainda n?o est? pronto para JDBC, aguardando..."
  sleep 5
done

echo "? MySQL est? completamente pronto para JDBC!"

# Iniciar a aplica??o Spring Boot
echo "Iniciando aplica??o Spring Boot..."
exec java -jar /app/app.jar
