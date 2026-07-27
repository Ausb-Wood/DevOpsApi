#!/usr/bin/env sh
# Dieses Skript startet eine festgelegte Gradle-Version.
# Es ist eine vereinfachte Lernversion des offiziellen Gradle Wrappers.

set -eu
# -e: bei Fehler abbrechen. -u: nicht gesetzte Variablen als Fehler behandeln.

GRADLE_VERSION="8.14.3" # Einheitliche Version für alle Teilnehmenden.
GRADLE_HOME="${GRADLE_USER_HOME:-$HOME/.gradle}/wrapper/dists/gradle-${GRADLE_VERSION}"
# ${A:-B} bedeutet: nutze A, sonst den Standardwert B.
GRADLE_BIN="$GRADLE_HOME/gradle-${GRADLE_VERSION}/bin/gradle"
ZIP_FILE="$GRADLE_HOME/gradle-${GRADLE_VERSION}-bin.zip"
URL="https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip"

if [ ! -x "$GRADLE_BIN" ]; then
  # ! -x bedeutet: Datei fehlt oder ist nicht ausführbar.
  command -v java >/dev/null 2>&1 || { echo "Fehler: Java/JDK fehlt." >&2; exit 1; }
  command -v curl >/dev/null 2>&1 || { echo "Fehler: curl fehlt." >&2; exit 1; }
  command -v unzip >/dev/null 2>&1 || { echo "Fehler: unzip fehlt." >&2; exit 1; }

  mkdir -p "$GRADLE_HOME" # -p erstellt auch fehlende Elternordner.
  echo "Gradle ${GRADLE_VERSION} wird von ${URL} geladen ..."
  curl --fail --location --retry 3 --output "$ZIP_FILE" "$URL"
  # --fail meldet HTTP-Fehler; --location folgt Umleitungen; --retry wiederholt Fehler.
  unzip -q -o "$ZIP_FILE" -d "$GRADLE_HOME" # -q leise; -o überschreibt vorhandene Dateien.
  rm -f "$ZIP_FILE" # Entfernt die nicht mehr benötigte ZIP-Datei.
fi

PROJECT_DIR="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"
# Ermittelt den Ordner, in dem dieses Skript liegt.
exec "$GRADLE_BIN" -p "$PROJECT_DIR" "$@"
# exec ersetzt das Skript durch Gradle; "$@" reicht alle Benutzerargumente weiter.
