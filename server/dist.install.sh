mkdir /usr/share/evaluationserver
mkdir /usr/share/evaluationserver/server
mkdir /etc/evaluationserver
mkdir /etc/evaluationserver/server
mkdir /var/log/evaluationserver

cp evaluationserver-server.jar /usr/share/evaluationserver/server/
cp tracer /usr/share/evaluationserver/server/
cp run.sh /usr/share/evaluationserver/server/
cp service.sh /etc/init.d/evaluationserver-server
chmod +x /etc/init.d/evaluationserver-server
cp -R lib /usr/share/evaluationserver/server/
cp config/configuration.xml /etc/evaluationserver/server/
cp config/service.config.properties /etc/evaluationserver/server/config.properties
cp config/service.logging.properties /etc/evaluationserver/server/logging.properties
ln -s /etc/evaluationserver/server /usr/share/evaluationserver/server/config
echo "#!/bin/bash\ncd /usr/share/evaluationserver/server/\nexec ./run.sh" > /usr/sbin/evaluationserver-server
chmod +x /usr/sbin/evaluationserver-server
