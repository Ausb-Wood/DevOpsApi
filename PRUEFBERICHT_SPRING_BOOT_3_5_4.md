# Prüfbericht: Spring Boot 3.5.4

## Geprüfte Versionen

- Java 21
- Spring Boot 3.5.4
- Gradle 8.14.3
- Jackson 2 über Spring Boot Dependency Management

## Durchgeführte Prüfungen

- `build.gradle` auf Spring Boot 3.5.4 umgestellt
- keine Spring-Boot-4-Pluginversion im Code oder in Konfigurationen
- keine `tools.jackson`-Imports im Quellcode
- vorhandener Jackson-Import verwendet `com.fasterxml.jackson.databind.ObjectMapper`
- keine manuell festgelegte Jackson-Version
- Java Toolchain bleibt auf Java 21
- Shell-Syntax von `gradlew` geprüft
- YAML-Syntax von GitHub Actions, Compose, Kubernetes und Ansible geprüft
- Docker-JAR-Pfad mit der Gradle-`bootJar`-Konfiguration abgeglichen
- Kommentare in Java-, Gradle-, Docker-, YAML-, Terraform- und Properties-Dateien geprüft

## Buildprüfung

Der vollständige Build wurde gestartet, konnte in der Erstellungsumgebung jedoch nicht abgeschlossen werden, weil der Host `services.gradle.org` dort nicht per DNS aufgelöst werden konnte. Das ist ein Infrastrukturfehler der Erstellungsumgebung und kein festgestellter Quellcodefehler.

Abschließender Test auf der Ubuntu-VM:

```bash
chmod +x gradlew
./gradlew --no-daemon clean build
./gradlew bootRun
```

In einem zweiten Terminal:

```bash
curl http://localhost:8080/api/health
curl http://localhost:8080/api/tasks
```

## Versionssuche

```bash
grep -RInE "version '4\.|import tools\.jackson|Spring Boot 4\." . \
  --exclude-dir=.git \
  --exclude-dir=build
```

Bei korrektem Stand liefert dieser Befehl keine Treffer in Code oder Konfiguration.
