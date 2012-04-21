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
cp server/dist.install.sh dist/server/install.sh
cp server/dist.service.sh dist/server/service.sh

chmod +x dist/server/run.sh
chmod +x dist/server/install.sh
chmod +x dist/server/service.sh

cp database.mysql.sql dist/database.mysql.sql

ant -file server/nbproject/build-impl.xml clean


cd tracer/
make CONF=Release build
if [ -e "dist/Release/GNU-Linux-x86/tracer" ]; then
	cp dist/Release/GNU-Linux-x86/tracer ../dist/server/
fi
chmod +x ../dist/server/tracer
make CONF=Release clean
cd ..


mkdir dist/web
cp -r web/evaluationserver/* dist/web/
rm dist/web/conf/application.conf
mv dist/web/conf/dist.application.conf dist/web/conf/application.conf
cp web/dist.install.sh dist/web/install.sh
cp web/dist.service.sh dist/web/service.sh

chmod +x dist/web/install.sh
chmod +x dist/web/service.sh


cp dist.uninstall.sh dist/uninstall.sh
chmod +x dist/uninstall.sh
