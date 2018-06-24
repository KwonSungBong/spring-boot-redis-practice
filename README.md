# spring-boot-redis-practice

https://github.com/spring-guides/gs-multi-module/tree/master/complete

http://wonwoo.ml/index.php/post/729


####################################################################################


mvn compile

mvn test

mvn install

mvn clean package


mvn spring-boot:run



######################################################################################################

http://wonwoo.ml/index.php/post/704

curl -X POST http://localhost:9000/hello
curl -X GET http://localhost:9000/hello

protected-mode no
CONFIG SET protected-mode no
CONFIG GET protected-mode


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

192.168.35.160

####################################################################


https://rorlab.gitbooks.io/railsguidebook/content/appendices/ubuntu16server.html
https://www.linode.com/docs/applications/big-data/how-to-install-and-configure-a-redis-cluster-on-ubuntu-1604/
http://crystalcube.co.kr/175?category=665742
http://crystalcube.co.kr/176?category=665742
http://crystalcube.co.kr/177?category=665742



sudo apt-get update && sudo apt-get upgrade

sudo apt install make gcc libc6-dev tcl


wget http://download.redis.io/releases/redis-4.0.8.tar.gz

tar xvzf redis-4.0.8.tar.gz

cd redis-4.0.8

sudo make install


sudo ./utils/install_server.sh

master port : 7000

slave port : 7001, 7002, 7003


sudo cp sentinel.conf /etc/redis/stn27000.conf

sudo cp sentinel.conf /etc/redis/stn27001.conf

sudo cp sentinel.conf /etc/redis/stn27002.conf

211.49.172.81


daemonize yes
pidfile /var/run/sentinel_27000.pid
logfile /var/log/sentinel_27000.log



cd /etc/init.d
sudo cp redis_7000 sentinel_27000
sudo cp sentinel_27000 sentinel_27001
sudo cp sentinel_27000 sentinel_27002


......................................................


sudo /etc/init.d/redis_7000 start
sudo /etc/init.d/redis_7001 start
sudo /etc/init.d/redis_7002 start
sudo /etc/init.d/redis_7003 start

sudo /etc/init.d/sentinel_27000 start
sudo /etc/init.d/sentinel_27001 start
sudo /etc/init.d/sentinel_27002 start


......................................................


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


redis-cli -p 27000 -a mypassword
redis-cli -p 27001 -a mypassword
redis-cli -p 27002 -a mypassword
shutdown
quit

sudo /etc/init.d/sentinel_27000 stop
sudo /etc/init.d/sentinel_27001 stop
sudo /etc/init.d/sentinel_27002 stop


......................................................


ps aux | grep redis

tail -n 16 /var/log/redis_7000.log
tail -n 16 /var/log/redis_7001.log
tail -n 16 /var/log/redis_7002.log
tail -n 16 /var/log/redis_7003.log


tail -n 30 /var/log/sentinel_8000.log
tail -n 30 /var/log/sentinel_8001.log
tail -n 30 /var/log/sentinel_8002.log