#!/bin/sh

mvn install:install-file -Dfile=lib/antelope.jar -DgroupId=org.tigris.antelope -DartifactId=antelopetasks -Dversion=3.4.0 -Dpackaging=jar
mvn install:install-file -Dfile=lib/jodd.jar -DgroupId=org.jodd -DartifactId=jodd -Dversion=3.4.8 -Dpackaging=jar
mvn install:install-file -Dfile=lib/jdbc-stdext-2.0.jar -DgroupId=javax.sql -DartifactId=jdbc-stdext -Dversion=2.0 -Dpackaging=jar
