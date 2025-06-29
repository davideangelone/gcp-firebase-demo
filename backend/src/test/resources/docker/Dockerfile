FROM openjdk:17-jdk-slim

# Installa Node.js (versione 18) e npm
RUN apt-get update && apt-get install -y curl \
 && curl -fsSL https://deb.nodesource.com/setup_18.x | bash - \
 && apt-get install -y nodejs \
 && npm install -g firebase-tools \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY firebase.json .

EXPOSE 9080

CMD ["firebase", "emulators:start", "--only", "firestore", "--project", "demo-project"]
