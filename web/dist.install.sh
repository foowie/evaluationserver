# Destination directories
INSTALL_DIR=/usr/share/evaluationserver/web
CONFIG_DIR=/etc/evaluationserver/web
LOG_DIR=/var/log/evaluationserver/web

# Create directories
if [ ! -d $INSTALL_DIR ]; then
	mkdir -p $INSTALL_DIR
fi
if [ ! -d $CONFIG_DIR ]; then
	mkdir -p $CONFIG_DIR
fi
if [ ! -d $LOG_DIR ]; then
	mkdir -p $LOG_DIR
fi

# Copy files into destination
cp -R ../web/* $INSTALL_DIR/
rm $INSTALL_DIR/install.sh
rm $INSTALL_DIR/service.sh

# Move configuration and log
mv $INSTALL_DIR/conf/* $CONFIG_DIR/
rm -R $INSTALL_DIR/conf
if [ -d $INSTALL_DIR/logs ]; then
	rm -R $INSTALL_DIR/logs
fi
ln -s $CONFIG_DIR $INSTALL_DIR/conf
ln -s $LOG_DIR $INSTALL_DIR/logs
chmod 600 -R $CONFIG_DIR/*

# Create init.d script
cp service.sh /etc/init.d/evaluationserver-web

# Create run script
echo "#!/bin/bash
cd $INSTALL_DIR/
play run" > /usr/sbin/evaluationserver-web
chmod +x /usr/sbin/evaluationserver-web

# Initialize play project
cd $INSTALL_DIR/
play dependencies
play secret

