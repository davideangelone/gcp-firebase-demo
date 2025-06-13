# Applicazione demo Spring Cloud Firebase (GCP)

Applicazione demo per leggere e scrivere messaggi sul database *Firebase Firestore*.
Utilizza i moduli **Authentication** e **Firestore** di **Firebase (GCP)**, ed è composta da:
- un backend realizzato con **Spring Boot**
- un frontend sviluppato con **Angular**

 

---

## 🔐 Autenticazione

Per autenticarsi è necessario:
- registrare le credenziali utente (email e password) nel modulo **Firebase Authentication**
- generare una chiave JSON di servizio (service account key) dalla **Google Cloud Console** (`IAM & Admin → Service Accounts`) usata per firmare i token JWT nel modulo di autenticazione

---

## 🌍 Configurazione

Per il **backend**, soltanto per deploy in ambienti esterni a GCP (ad esempio in locale), è necessario configurare la variabile di ambiente `GOOGLE_APPLICATION_CREDENTIALS` col percorso assoluto al file JSON del service account.

Per il **frontend** bisogna configurare il file .env con le proprietà ritornate in fase di creazione dell'applicazione su Firebase

