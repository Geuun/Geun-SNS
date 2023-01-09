SELECT @@profiling;

SET profiling = 1;

SELECT @@profiling;

SHOW profiles;

SET profiling = 0;

SELECT @@profiling;