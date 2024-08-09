## DockerFile ##
FROM openjdk:17
ENV PORT 7080
EXPOSE 7080 
ENV APP_NAME=state-machine 
RUN mkdir -p/opt 
ADD ./target/$APP_NAME-bin.tar /opt/ 
COPY source dest 
RUN mkdir /opt/jasypt 
CMD [ "/bin/sh","-c","./start.sh ${ENV_TYPE} $APP_NAME" ]