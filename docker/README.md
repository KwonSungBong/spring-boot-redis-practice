####################################################################################

https://github.com/lgaticaq/redis-sentinel

https://github.com/lgaticaq/redis-cluster-sentinel


export REDIS_PASSWORD=mypassword
docker-compose up -d
docker-compose scale slave=2 sentinel=3


sentinel get-master-addr-by-name master


docker exec -it temp_master_1 redis-cli info | grep role
docker exec -it temp_slave_1 redis-cli info | grep role
docker exec -it temp_slave_2 redis-cli info | grep role
docker logs temp_sentinel_1


docker exec -it temp_master_1 redis-cli -a redis1234


docker inspect ...

######################################################################################################

docker exec -it redis-sentinel-docker_redis_master_1 redis-cli




######################################################################################################
192.168.35.179
ksb/password

export REDIS_PASSWORD=mypassword
docker-compose up -d
docker-compose scale slave=2 sentinel=3


