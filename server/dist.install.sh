# Destination directories
# After installation change directory also in /etc/init.d/evaluationserver-web
INSTALL_DIR=/usr/share/evaluationserver/server
CONFIG_DIR=/etc/evaluationserver/server
LOG_DIR=/var/log/evaluationserver/server

# Create directories if not exists
if [ ! -d $INSTALL_DIR ]; then
	mkdir -p $INSTALL_DIR
fi
if [ ! -d $CONFIG_DIR ]; then
	mkdir -p $CONFIG_DIR
fi
if [ ! -d $LOG_DIR ]; then
	mkdir -p $LOG_DIR
fi

# Copy files to destination directory
cp evaluationserver-server.jar $INSTALL_DIR/
cp tracer $INSTALL_DIR/
cp run.sh $INSTALL_DIR/

# Copy init.d script
cp service.sh /etc/init.d/evaluationserver-server
chmod +x /etc/init.d/evaluationserver-server

# Copy libraries to destination directory
cp -R lib $INSTALL_DIR/

# Copy configuration files
cp config/configuration.xml $CONFIG_DIR/
cp config/service.config.properties $CONFIG_DIR/config.properties
cp config/service.logging.properties $CONFIG_DIR/logging.properties
echo "java.util.logging.FileHandler.pattern = $LOG_DIR/server.log" >> $CONFIG_DIR/logging.properties
ln -s $CONFIG_DIR $INSTALL_DIR/config
chmod 600 -R $CONFIG_DIR/*

# Create run script
echo "#!/bin/bash
cd $INSTALL_DIR/
exec ./run.sh" > /usr/sbin/evaluationserver-server
chmod +x /usr/sbin/evaluationserver-server
