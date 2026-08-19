# bash

DOCKER_FILES_DIR="../docker"
DOCKER_DEV_DIR="$DOCKER_FILES_DIR/dev"
DOCKER_PROD_DIR="$DOCKER_FILES_DIR/prod"
NEWSLETTER_TARGET_DIR="../newsletter-service/target"

if ["$#" -lt 1]; then
  if ["$1" == "-p"]
    DOCKER_BUILD_DIR="$DOCKER_PROD_DIR"
else
  DOCKER_BUILD_DIR="$DOCKER_DEV_DIR"
fi

mkdir -p "$DOCKER_BUILD_DIR"

../mvnw clean install -f ../pom.xml

mkdir -p "$DOCKER_FILES_DIR"
cp "$NEWSLETTER_TARGET_DIR/newsletter-service-*.jar" "$DOCKER_FILES_DIR"