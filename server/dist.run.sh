#!/bin/bash
exec java -Djava.util.logging.config.file="config/logging.properties" -javaagent:lib/org.springframework.instrument-3.1.1.RELEASE.jar -jar evaluationserver-server.jar
