DECLARE @db_name sysname = N'$(MSSQL_DATABASE)';

IF DB_ID(@db_name) IS NULL
BEGIN
    DECLARE @sql NVARCHAR(MAX);
    SET @sql = N'CREATE DATABASE [' + REPLACE(@db_name, N']', N']]') + N']';
    EXEC(@sql);
END;
GO
