#!/bin/bash

if [ -d "dist" ]; then
    rm -r dist
fi

mkdir dist

mkdir dist/server
mkdir dist/server/config
mkdir dist/server/temp

ant -file server/nbproject/build-impl.xml jar
cp -r server/dist/* dist/server/
rm dist/server/README.TXT

cp server/config/dist.logging.properties dist/server/config/logging.properties
cp server/config/dist.config.properties dist/server/config/config.properties
cp server/config/configuration.xml dist/server/config/configuration.xml
cp server/config/service.logging.properties dist/server/config/service.logging.properties
cp server/config/service.config.properties dist/server/config/service.config.properties

cp server/dist.run.sh dist/server/run.sh
cp server/dist.install.sh dist/server/install-service.sh
cp server/dist.uninstall.sh dist/server/uninstall-service.sh
cp server/dist.service.sh dist/server/service.sh

chmod +x dist/server/run.sh
chmod +x dist/server/install-service.sh
chmod +x dist/server/uninstall-service.sh
chmod +x dist/server/service.sh

cp database.mysql.sql dist/database.mysql.sql

ant -file server/nbproject/build-impl.xml clean


cd tracer/
make CONF=Release build
cp dist/Release/GNU-Linux-x86/tracer ../dist/server/
chmod +x ../dist/server/tracer
make CONF=Release clean
cd ..
