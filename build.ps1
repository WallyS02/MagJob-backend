mvn clean package verify
$VERSION="0.0.1"
docker build -t magjob-backend:$VERSION .
