#!/bin/sh

# stop service
sudo service gui stop

# remove jar
sudo rm -rfv /home/gui/gui.jar

# copy new jar
sudo cp -v my-fitness/my-strava-gui/target/my-strava-gui-1.0.0.jar /home/gui/gui.jar

# set rights
sudo chown gui:apps /home/gui/gui.jar

# create service
sudo cp my-fitness/deploy/gui.service /etc/systemd/system

# reload service
sudo systemctl daemon-reload

# start service
sudo service gui start

# status
sudo service gui status

# get public url
curl -s 'http://localhost:4040/api/tunnels' | jq -r '.tunnels[0].public_url'