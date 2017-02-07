FROM openjdk:8

WORKDIR ~/
RUN apt-get update && apt-get install -y git && git clone https://github.com/pinussilvestrus/bncl.git
WORKDIR bncl
RUN git checkout develop && ./gradlew build
EXPOSE 8080
CMD java -Durl=mongodb -jar web/build/libs/bnclDemo-0.1.0.jar