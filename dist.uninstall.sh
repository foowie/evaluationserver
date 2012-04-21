if [ -d "/usr/share/evaluationserver" ]; then
	rm -Rf /usr/share/evaluationserver
fi
if [ -d "/etc/evaluationserver" ]; then
	rm -Rf /etc/evaluationserver
fi
if [ -d "/var/log/evaluationserver" ]; then
	rm -Rf /var/log/evaluationserver
fi
if [ -e "/usr/sbin/evaluationserver-server" ]; then
	rm /usr/sbin/evaluationserver-server
fi
if [ -e "/usr/sbin/evaluationserver-web" ]; then
	rm /usr/sbin/evaluationserver-web
fi
if [ -e "/etc/init.d/evaluationserver-server" ]; then
	rm /etc/init.d/evaluationserver-server
fi
if [ -e "/etc/init.d/evaluationserver-web" ]; then
	rm /etc/init.d/evaluationserver-web
fi
if [ -e "/var/cache/evaluationserver" ]; then
	rm /var/cache/evaluationserver
fi
