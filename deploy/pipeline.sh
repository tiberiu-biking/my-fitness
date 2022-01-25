cd /home/pi/dev/github/my-fitness
git checkout master
git pull
git status
git log -n 1
mvn clean install -DskipTests
cd /home/pi/dev/github
pwd
./deploy.sh