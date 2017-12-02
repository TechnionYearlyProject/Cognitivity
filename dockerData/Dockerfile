# dockerfile for mysql docker
FROM mysql
ENV MYSQL_ROOT_PASSWORD password
ADD /db_settings /
ADD /construct_db.sh /
EXPOSE 3306
