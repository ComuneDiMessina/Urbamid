#!/bin/sh

"$M2_HOME"/bin/mvn install:install-file -Dfile=jts-core-1.16.1.jar -DgroupId=org.locationtech -DartifactId=jts-core -Dversion=1.16.1 -Dpackaging=jar
"$M2_HOME"/bin/mvn install:install-file -Dfile=gt-main-24-SNAPSHOT.jar -DgroupId=org.geotools -DartifactId=gt-main -Dversion=24-SNAPSHOT -Dpackaging=jar
"$M2_HOME"/bin/mvn install:install-file -Dfile=gt-opengis-24-SNAPSHOT.jar -DgroupId=org.geotools -DartifactId=gt-opengis -Dversion=24-SNAPSHOT -Dpackaging=jar
"$M2_HOME"/bin/mvn install:install-file -Dfile=gt-referencing-24-SNAPSHOT.jar -DgroupId=org.geotools -DartifactId=gt-referencing -Dversion=24-SNAPSHOT -Dpackaging=jar
"$M2_HOME"/bin/mvn install:install-file -Dfile=gt-shapefile-24-SNAPSHOT.jar -DgroupId=org.geotools -DartifactId=gt-shapefile -Dversion=24-SNAPSHOT -Dpackaging=jar
"$M2_HOME"/bin/mvn install:install-file -Dfile=gt-swing-24-SNAPSHOT.jar -DgroupId=org.geotools -DartifactId=gt-swing -Dversion=24-SNAPSHOT -Dpackaging=jar
pause
