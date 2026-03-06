#!/bin/sh
set -eu

until /opt/mssql-tools18/bin/sqlcmd -S sqlserver -U sa -P "${MSSQL_SA_PASSWORD}" -C -Q "SELECT 1" >/dev/null 2>&1
do
  echo "Aguardando SQL Server aceitar conexoes..."
  sleep 5
done

/opt/mssql-tools18/bin/sqlcmd \
  -S sqlserver \
  -U sa \
  -P "${MSSQL_SA_PASSWORD}" \
  -C \
  -v MSSQL_DATABASE="${MSSQL_DATABASE}" \
  -i /docker/sql/init.sql
