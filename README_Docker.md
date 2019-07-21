# Docker
All the microservices have been dockerized and published to the dockerhub. A simple installation using docker compose is also available.

## Docker Compose
* Please download the file "docker-compose.yml"
* Navigate with the cmd to the download folder where you have placed the file
* Please ensure that docker and docker compose are already installed!
* Run "docker-compose up" from within this folder
* The docker compose will download the microservices images' and the necessary images (such as mongodb) as well, create from each image a container, connect these containers using given network (see the docker-compose.yml file) and then start these containers.
* Now you can request all the API endpoints as the jars were locally installed.


