const fs = require('fs');
const dotenv = require('dotenv');

dotenv.config();

const generateFile = (isProd) => {
  const filename = isProd
    ? 'src/environments/environment.prod.ts'
    : 'src/environments/environment.ts';

  const config = `
export const environment = {
  production: ${isProd},
  apiUrl: '${process.env.API_URL}',
  firebaseConfig: {
    apiKey: '${process.env.API_KEY}',
    authDomain: '${process.env.AUTH_DOMAIN}',
    projectId: '${process.env.PROJECT_ID}',
    storageBucket: '${process.env.STORAGE_BUCKET}',
    messagingSenderId: '${process.env.MESSAGING_SENDER_ID}',
    appId: '${process.env.APP_ID}',
    measurementId: '${process.env.MEASUREMENT_ID}'
  }
};
  `;

  fs.writeFileSync(filename, config.trim());
  console.log(`✅ ${filename} generato con successo.`);
};

generateFile(false); // environment.ts
generateFile(true);  // environment.prod.ts