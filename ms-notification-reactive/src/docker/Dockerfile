# Usando JDK 21 a partir da imagem Eclipse Temurin
FROM eclipse-temurin:21-jre-alpine

# Definindo variáveis de ambiente
ENV JAVA_APP_JAR=@project.artifactId@-@project.version@.jar \
	TZ=America/Sao_Paulo \
	SPRING_PROFILES_ACTIVE="stack" \
	JAVA_OPTIONS="-Xmx256m" \
	CONTAINER_MAX_MEMORY=256m \
	JAVA_APP_NAME="@project.artifactId@" \
	AB_OFF=true

# Adicionando o arquivo JAR da aplicação
ADD $JAVA_APP_JAR /deployments/@project.artifactId@-@project.version@.jar

# Definindo o comando de inicialização da aplicação
CMD ["java", "-jar", "/deployments/@project.artifactId@-@project.version@.jar"]