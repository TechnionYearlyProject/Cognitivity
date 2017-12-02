# this file will be used inside the docker for purpose of defining the db
#!/bin/bash

#database file definitions
DB_FILE="db_settings"

settings=`cat ${DB_FILE}`

definitions="
DROP DATABASE IF EXISTS Cognitivity;
CREATE DATABASE Cognitivity;
USE Cognitivity;
${settings}"
mysql -h 0.0.0.0 -P3306 -uroot -p"password" -e"${definitions}"
