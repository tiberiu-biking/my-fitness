cd /home/pi/dev/github/my-fitness
git checkout master
git pull
git status
git log -n 1
mvn clean install -DskipTests
pwd
echo "Running deploy"
/home/pi/dev/github/my-fitness/deploy.sh