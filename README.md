# spring-boot-redis-practice

https://github.com/spring-guides/gs-multi-module/tree/master/complete

http://wonwoo.ml/index.php/post/729


####################################################################################


mvn compile

mvn test

mvn install

mvn clean package


mvn spring-boot:run


####################################################################################

https://github.com/lgaticaq/redis-sentinel

https://github.com/lgaticaq/redis-cluster-sentinel


export REDIS_PASSWORD=redis1234
docker-compose up -d
docker-compose scale slave=2 sentinel=3


sentinel get-master-addr-by-name master


docker exec -it temp_master_1 redis-cli info | grep role
docker exec -it temp_slave_1 redis-cli info | grep role
docker exec -it temp_slave_2 redis-cli info | grep role
docker logs temp_sentinel_1


docker exec -it temp_master_1 redis-cli -a redis1234


######################################################################################################

http://wonwoo.ml/index.php/post/704

curl -X POST http://localhost:9000/hello
curl -X GET http://localhost:9000/hello

protected-mode no
CONFIG SET protected-mode no
CONFIG GET protected-mode

docker inspect ...

######################################################################################################

docker exec -it redis-sentinel-docker_redis_master_1 redis-cli




######################################################################################################
ksb/password
https://www.linode.com/docs/applications/big-data/how-to-install-and-configure-a-redis-cluster-on-ubuntu-1604/



######################################################################################################

wget http://download.redis.io/releases/redis-4.0.8.tar.gz
tar xvzf redis-4.0.8.tar.gz
cd redis-4.0.8
sudo make install


sudo ./redis-4.0.8/utils/install_server.sh
sudo /etc/init.d/redis_6379 start
sudo /etc/init.d/redis_6379 stop

http://crystalcube.co.kr/175?category=665742


sudo /etc/init.d/redis_7000 stop
sudo /etc/init.d/redis_7001 stop
sudo /etc/init.d/redis_7002 stop
sudo /etc/init.d/redis_7003 stop

$ redis-cli -a mypassword
$ shutdown
$ quit

sudo /etc/init.d/redis_7000 start
sudo /etc/init.d/redis_7001 start
sudo /etc/init.d/redis_7002 start
sudo /etc/init.d/redis_7003 start

sudo cp sentinel.conf /etc/redis/stn8000.conf

sudo cp redis_7000 redis_stn_8000
sudo cp redis_7000 redis_stn_8001
sudo cp redis_7000 redis_stn_8002


..........


redis-cli -p 7000 -a mypassword
redis-cli -p 7001 -a mypassword
redis-cli -p 7002 -a mypassword
redis-cli -p 7003 -a mypassword
shutdown
quit

sudo /etc/init.d/redis_7000 stop
sudo /etc/init.d/redis_7001 stop
sudo /etc/init.d/redis_7002 stop
sudo /etc/init.d/redis_7003 stop

sudo /etc/init.d/sentinel_8000 stop
sudo /etc/init.d/sentinel_8001 stop
sudo /etc/init.d/sentinel_8002 stop


sudo /etc/init.d/redis_7000 start
sudo /etc/init.d/redis_7001 start
sudo /etc/init.d/redis_7002 start
sudo /etc/init.d/redis_7003 start

sudo /etc/init.d/sentinel_8000 start
sudo /etc/init.d/sentinel_8001 start
sudo /etc/init.d/sentinel_8002 start


ps aux | grep redis


tail -n 30 /var/log/sentinel_8000.log
tail -n 30 /var/log/sentinel_8001.log
tail -n 30 /var/log/sentinel_8002.log


####################################################################


https://rorlab.gitbooks.io/railsguidebook/content/appendices/ubuntu16server.html
https://www.linode.com/docs/applications/big-data/how-to-install-and-configure-a-redis-cluster-on-ubuntu-1604/
http://crystalcube.co.kr/175?category=665742
http://crystalcube.co.kr/176?category=665742
http://crystalcube.co.kr/177?category=665742






s