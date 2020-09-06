# Getting Started

### Reference Documentation
##Docker
# Run mysql standalone container for the first time
- docker run --name mysql-standalone -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=maths -e MYSQL_USER=sa -e MYSQL_PASSWORD=password -d mysql/mysql-server:latest

# Run mysql standalone container regularly
- docker run --name=mysql-standalone -d mysql/mysql-server:latest

# Dump data to docker mysql
- docker exec -i  mysql-standalone mysql -uroot -ppassword maths < maths.sql

# Docker mysql access
- docker exec -it mysql-standalone mysql -uroot -p

# Back up data
-  docker exec -i mysql-standalone /usr/bin/mysqldump -uroot -ppassword maths > backup.sql

# Docker run application
- docker exec -it mysql-standalone mysql -uroot -p

# Docker useful command
- docker ps -a
- docker stop xyz
- docker rmi abc
- docker rm qwe
- docker image ls

## Build and run application
# Build with docker
- docker build -t mathsimg .

# Run with docker

