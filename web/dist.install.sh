if [ ! -d "/usr/share/evaluationserver" ]; then
	mkdir /usr/share/evaluationserver
fi
mkdir /usr/share/evaluationserver/web
if [ ! -d "/etc/evaluationserver" ]; then
	mkdir /etc/evaluationserver
fi
if [ ! -d "/var/log/evaluationserver" ]; then
	mkdir /var/log/evaluationserver
fi

cp -R ../web /usr/share/evaluationserver/
rm /usr/share/evaluationserver/web/install.sh
rm /usr/share/evaluationserver/web/service.sh

ln -s /usr/share/evaluationserver/web/conf /etc/evaluationserver/web
ln -s /usr/share/evaluationserver/web/logs /var/log/evaluationserver/web

cp service.sh /etc/init.d/evaluationserver-web

echo "#!/bin/bash
cd /usr/share/evaluationserver/web/
play run" > /usr/sbin/evaluationserver-web
chmod +x /usr/sbin/evaluationserver-web

cd /usr/share/evaluationserver/web/
play dependencies
play secret

