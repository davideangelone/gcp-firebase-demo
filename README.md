# Applicazione demo per Google Cloud Firebase (GCP)

Applicazione demo per leggere e scrivere messaggi su database NoSQL.
Utilizza il modulo **Authentication** e il DB **Firestore** di **Firebase**, ed è composta da:
- un backend realizzato con **Spring Boot**
- un frontend sviluppato con **Angular**

E' presente una pipeline GitHub Actions per il deploy automatico in GCP (CI/CD).

Ovviamente, essendo un'applicazione demo, per semplicità non vengono seguite tutte le best practices di sicurezza e di configurazione dell'ambiente cloud

È necessario avere installato le CLI di Firebase e Google Cloud SDK (per lanciare i comandi `firebase` e `gcloud`).
In alternativa è possibile lanciare i comandi direttamente dalla Cloud Shell di GCP.

---

## 🔐 Autenticazione

E' necessario registrare le credenziali degli utenti autorizzati all'accesso dell'applicazione (email e password) nel modulo **Firebase Authentication**

Inoltre bisogna:
- generare una chiave JSON di servizio (service account key) dalla **Google Cloud Console** (sezione `IAM & Admin`) usata da Firebase Admin SDK per verificare la firma dei token JWT
- generare una chiave JSON di servizio (service account key) dalla **Google Cloud Console** (sezione `IAM & Admin`) usata dal plugin maven Jib per depositare su GCP Artifact Registry l'immagine Docker del backend. Bisogna assegnare i ruoli `Artifact Registry Create-on-Push Writer` e `Artifact Registry Writer` al service account `jib-builder@<PROJECT_ID>.iam.gserviceaccount.com`

---

## 🌍 Configurazione

Per il **backend**, soltanto per deploy in ambienti esterni a GCP (ad esempio in locale), è necessario configurare la variabile di ambiente `GOOGLE_APPLICATION_CREDENTIALS` col percorso assoluto al file JSON delle credenziali del service account.

Per il **frontend**, soltanto per deploy in ambienti esterni a GCP (ad esempio in locale), bisogna configurare il file `.env` con le proprietà ritornate in fase di creazione dell'applicazione su Firebase.
Durante l'esecuzione della pipeline GitHub Actions, questo file viene generato automaticamente.

L'applicazione necessita la configurazione di alcuni secrets sul repository GitHub, ricavati dalla chiave JSON del service account:
- `API_KEY`
- `AUTH_DOMAIN`
- `PROJECT_ID`
- `STORAGE_BUCKET`
- `MESSAGING_SENDER_ID`
- `APP_ID`
- `MEASUREMENT_ID`
- `GCP_CREDENTIALS_JSON` (contenuto del file JSON delle credenziali del service account di Jib)

Inoltre è necessario configurare i secrets:
- `API_URL` con l'url del backend su Cloud Run
- `FIREBASE_DEPLOY_TOKEN` col valore del token di deploy (per il deploy del frontend su Firebase Hosting). Per ottenere il token di deploy, bisogna utilizzare il comando `firebase login:ci`


Per il backend, per concedere l'autorizzazione alla pipeline al deploy su Cloud Run, bisogna lanciare i seguenti comandi:
```
gcloud artifacts repositories add-iam-policy-binding gcp-app-demo \
  --location=europe-west1 \
  --member="serviceAccount:jib-builder@<PROJECT_ID>.iam.gserviceaccount.com" \
  --role="roles/artifactregistry.reader" \
  --project=<PROJECT_ID>
  
gcloud projects add-iam-policy-binding <PROJECT_ID> \
  --member="serviceAccount:jib-builder@<PROJECT_ID>.iam.gserviceaccount.com" \
  --role="roles/run.admin"

gcloud projects add-iam-policy-binding <PROJECT_ID> \
  --member="serviceAccount:jib-builder@<PROJECT_ID>.iam.gserviceaccount.com" \
  --role="roles/iam.serviceAccountUser"
```

---

## 🧪 Test

Nel **backend**, per eseguire l'integration test in locale (basato su *TestContainers*):
- È necessario impostare la variabile di ambiente `TESTCONTAINERS_RYUK_DISABLED` a `true` per disabilitare Ryuk.
- Se il docker engine è in ascolto sul protocollo IPv6 (ad esempio quando installato su WSL2 di Windows) è necessario utilizzare la JVM option `-Djava.net.preferIPv6Addresses=true`.

