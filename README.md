# Task API – Startrepository für den DevOps-Kurs

## Verwendete Versionen

- Java 21
- Spring Boot 3.5.4
- Gradle 8.14.3 über `./gradlew`
- Jackson 2 über `spring-boot-starter-web`

> Spring Boot verwaltet die passenden Jackson-Abhängigkeiten. Keine eigene Jackson-Version ergänzen, solange keine besondere Anforderung besteht.


Dieses Repository enthält eine kleine Spring-Boot-REST-Anwendung, die während des Kurses erweitert, getestet, containerisiert und mit Kubernetes bereitgestellt wird.

## Voraussetzungen

- Ubuntu 24.04 LTS oder vergleichbar
- Git
- Java Development Kit 21
- `curl`, `unzip` und `ca-certificates`
- Docker
- Minikube und `kubectl` ab dem Kubernetes-Teil

Gradle muss nicht global installiert werden. Das Repository enthält `./gradlew` und legt Gradle 8.14.3 fest.

## Schnellstart

```bash
git clone <REPOSITORY-URL>
cd DevOpsApi
chmod +x gradlew
./gradlew --version
./gradlew clean test
./gradlew bootRun
```

Anschließend:

```bash
curl http://localhost:8080/api/health
curl http://localhost:8080/api/tasks
```

Aufgabe anlegen:

```bash
curl -i -X POST http://localhost:8080/api/tasks \
  -H 'Content-Type: application/json' \
  -d '{"title":"Gradle verstehen"}'
```

## Wichtige Gradle-Befehle

```bash
./gradlew test          # Unit- und Webtests
./gradlew clean build   # vollständiger Build
./gradlew bootRun       # Anwendung starten
./gradlew bootJar       # ausführbare JAR-Datei erzeugen
./gradlew tasks         # verfügbare Tasks
```

## Projektstruktur

```text
src/main/java/          Anwendungscode
src/main/resources/     Anwendungskonfiguration
src/test/java/          automatisierte Tests
.github/workflows/      GitHub-Actions-Pipeline
k8s/                    Kubernetes-Ressourcen
ansible/                optionales Konfigurationsbeispiel
terraform/              optionales IaC-Beispiel
build.gradle            Build- und Abhängigkeitsdefinition
settings.gradle         Projektname
gradlew                 projektgebundener Gradle-Start
Dockerfile              Container-Build
compose.yaml            lokaler Containerstart
```

## Kursaufgaben

Mindestens eine fachliche Erweiterung ist selbst zu programmieren, beispielsweise:

- Aufgabe löschen
- Titel bearbeiten
- nach Status filtern
- Priorität ergänzen
- Datenbank anbinden

Jede Erweiterung benötigt nachvollziehbare Commits, Tests und Dokumentation.
