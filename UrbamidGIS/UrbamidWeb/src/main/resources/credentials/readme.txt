Passi per creare un keystore a partire da una chiave privata

openssl req -new -newkey rsa:4096 -sha512 -nodes -keyout urbamid.key -x509 -days 3650 -out urbamid.crt

openssl pkcs12  -export -out urbamid.pfx -inkey urbamid.key -in urbamid.crt -name urbamid

keytool -importkeystore -srckeystore urbamid.pfx -srcstoretype pkcs12 -destkeystore urbamid-keystore.jks -deststoretype pkcs12

pwd: urbamidsit


in resources

#### generazione del keystore
keytool -genkey -keyalg RSA -alias saml2cert -keystore keystore.jks
Immettere la password del keystore: urbamidsit
Specificare nome e cognome
  [Urbamid]:  UrbamidSIT
Specificare il nome dell'unità organizzativa
  [Comune]:  Urbanistica
Specificare il nome dell'organizzazione
  [Comune Messina]:  Comune Messina
Specificare la località
  [Messina]:  Messina
Specificare la provincia
  [ME]:  ME
Specificare il codice a due lettere del paese in cui si trova l'unità
  [IT]:  IT
Il dato CN=UrbamidSIT, OU=Urbanistica, O=Comune Messina, L=Messina, ST=ME, C=IT è corretto?
  [no]:  si


#### generazione del certificato
keytool -export -keystore keystore.jks -alias saml2cert -file saml2.cer

# generazione del pem file
openssl x509 -inform der -in saml2.cer -out saml2.pem	


##### Riferimenti IAM Messina

mail di L.Ignudo@almaviva.it del 02/04/20, 16:19 
Buon giorno Luigi,

I test di integrazione con il vosto IAM hanno dato esito positivo, tuttavia abbiamo dovuto adoperare i seguenti accorgimenti per poter portare a termine i test e quindi la fase di login.
Ci sono problemi di CORS, i vostri server non espongono gli header di origin considerando il nostro host, come si evidenzia dal questo messaggio di errore

test-pc.municipia.eng.it/:1 

Access to XMLHttpRequest at 'https://spid-test.comune.messina.it/oauth2/oidcdiscovery/.well-known/openid-configuration' 

from origin 'https://test-pc.municipia.eng.it' has been blocked by CORS policy: 

Response to preflight request doesn't pass access control check: The 'Access-Control-Allow-Origin' 

header has a value 'https://zeebe-manager-test.comune.messina.it' that is not equal to the supplied origin.

E' configurato solo zeebe-manager come accessibile; ho forzato chrome in modo che non desse peso alle policy CORS
in questo modo ho potuto chiamare correttamente le url esposte dal vostro wso2 e fare login con gli utenti che mi avete comunicato.
Il token arriva come da voi indicato

{

  "at_hash": "GWmSwYC-QnUECEDUErpk8g",

  "sub": "eng1",

  "amr": [],

  "iss": "https://spid-test.comune.messina.it:443/oauth2/token",

  "groups": "Internal/everyone",

  "given_name": "Mario",

  "nonce": "N0.2234885356624271585130601419",

  "sid": "872c2099-1af0-4467-a203-b22be12766b2",

  "aud": "wfHfxzbcfS9IP6OUL7uOlTfYby4a",

  "azp": "wfHfxzbcfS9IP6OUL7uOlTfYby4a",

  "nickname": "RSSMRA39D05E131X",

  "exp": 1585134207,

  "iat": 1585130607,

  "family_name": "Rossi"

}

Ho notato che i gruppi sono una stringa semplice, ho dovuto apportare una patch al nostro frontend
per normalizzare la lettura dei groups in quanto noi li intendiamo come lista di ruoli anche se di tipo singolare.
ie:

  "groups": ["Internal/everyone"]

Non ho potuto testare il logout in quanto la url di post logout non matcha quella configurata sul service provider, probabilmente c'è configurata solo la callback.


192.168.76.38 spid-testenv-test.comune.messina.it spid-test.comune.messina.it portal-test.comune.messina.it gw-test.comune.messina.it eigtw-dev.comune.messina.it ei-dev.comune.messina.it documentale-test.comune.messina.it simplemonitor.comune.messina.it usertask-test.comune.messina.it operate.comune.messina.it bolite-test.comune.messina.it room-tst.comune.messina.it

##### OPENCONNECTID Seguire questa guida:
https://openclassrooms.com/en/courses/5683681-secure-your-web-application-with-spring-security/6695831-configure-oauth-2-0-with-openid-connect-on-a-spring-web-application
https://www.baeldung.com/spring-security-openid-connect




