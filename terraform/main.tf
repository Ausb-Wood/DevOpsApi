# Terraform beschreibt den gewünschten Zustand deklarativ.
terraform {
  required_providers {
    local = {
      source  = "hashicorp/local" # Anbieter des Providers.
      version = "~> 2.5" # Erlaubt kompatible 2.x-Versionen ab 2.5.
    }
  }
}

# resource ist ein verwaltetes Objekt; local_file erzeugt eine lokale Datei.
resource "local_file" "course_info" {
  filename = "${path.module}/generated-course-info.txt" # path.module ist dieser Ordner.
  content  = "DevOps-Kursumgebung wurde deklarativ erzeugt.\n"
}
