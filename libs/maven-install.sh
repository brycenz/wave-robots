#!/bin/sh

FLEXJSON_VERSION=1.8
 
mvn install:install-file -Dfile=flexjson-${FLEXJSON_VERSION}.jar \
  -DgroupId=flexjson \
  -DartifactId=flexjson \
  -Dversion=${FLEXJSON_VERSION} \
  -Dpackaging=jar \
  -DgeneratePom=true

JAVAX_JDO_VERSION=2.3-eb
 
mvn install:install-file -Dfile=jdo2-api-${JAVAX_JDO_VERSION}.jar \
  -DgroupId=javax.jdo \
  -DartifactId=jdo2-api \
  -Dversion=${JAVAX_JDO_VERSION} \
  -Dpackaging=jar \
  -DgeneratePom=true

JTA_VERSION=1.1
 
mvn install:install-file -Dfile=jta-api-${JTA_VERSION}.jar \
  -DgroupId=javax.transaction \
  -DartifactId=transaction-api \
  -Dversion=${JTA_VERSION} \
  -Dpackaging=jar \
  -DgeneratePom=true
