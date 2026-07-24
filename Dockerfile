# Stufe 1 baut die Anwendung. AS build gibt dieser Stufe einen Namen.
FROM gradle:8.14.3-jdk21 AS build

#Hier müssen sie das Zertifikat kopieren das ihnen bislang probleme gemacht hat. 
COPY cert02.crt /usr/local/share/ca-certificates/cert02.crt

RUN update-ca-certificates

RUN keytool -importcert \
    -noprompt \
    -trustcacerts \
    -alias cert02-ca \
    -file /usr/local/share/ca-certificates/cert02.crt \
    -keystore $JAVA_HOME/lib/security/cacerts \
    -storepass changeit

# Alle folgenden Befehle arbeiten in diesem Ordner.
WORKDIR /workspace

# Kopiert Builddateien und Quellcode in das Image.
#COPY build.gradle settings.gradle ./
#COPY src ./src

COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY src src

RUN chmod +x gradlew
RUN ./gradlew --no-daemon clean bootJar

# RUN führt den Befehl beim Image-Bau aus.
RUN gradle --no-daemon clean bootJar
# --no-daemon beendet Gradle danach; clean löscht alte Ergebnisse; bootJar baut die startbare JAR.

# Stufe 2 enthält nur Java zur Ausführung und bleibt dadurch kleiner.
FROM eclipse-temurin:21-jre
WORKDIR /app

# Installiert curl für den Healthcheck und entfernt danach den Paketcache.
RUN apt-get update \
    && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/*

# Erstellt einen Benutzer ohne Administratorrechte.
RUN useradd --system --uid 10001 appuser

# Kopiert nur das fertige Programm aus der Build-Stufe.
COPY --from=build /workspace/build/libs/task-api.jar /app/app.jar

# Ab hier läuft der Container als eingeschränkter Benutzer.
USER appuser
# Dokumentiert den verwendeten Anwendungsport.
EXPOSE 8080
# Startbefehl des Containers.
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
