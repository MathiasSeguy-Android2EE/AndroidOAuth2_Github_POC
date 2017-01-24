The publicKeyStore.jks has android has password and contain only one key which is called public and has android as password.

C:\Program Files\Java\jdk1.8.0_101\bin>keytool -exportcert -keystore D:\Android\Git\ACMS\AndroidOAuth2_Github_POC\app\publicKeyStore.jks -list -v
Entrez le mot de passe du fichier de clés :

Type de fichier de clés : JKS
Fournisseur de fichier de clés : SUN

Votre fichier de clés d'accès contient 1 entrée

Nom d'alias : public
Date de création : 24 janv. 2017
Type d'entrée : PrivateKeyEntry
Longueur de chaîne du certificat : 1
Certificat[1]:
Propriétaire : CN=Mathias Seguy, OU=Sylpheo, O=ACMS, L=Paris, ST=Paris, C=Fr
Emetteur : CN=Mathias Seguy, OU=Sylpheo, O=ACMS, L=Paris, ST=Paris, C=Fr
Numéro de série : 685e5ca0
Valide du : Tue Jan 24 11:19:13 CET 2017 au : Sat Jan 18 11:19:13 CET 2042
Empreintes du certificat :
         MD5:  AD:C2:C7:05:81:22:9F:CA:A2:4D:40:10:8A:37:2E:5E
         SHA1 : 05:84:A1:81:3B:39:3B:A9:29:C4:26:80:6C:96:34:E2:E9:A6:66:07
         SHA256 : 9E:35:6F:C1:50:A7:3D:13:78:D2:43:2B:58:48:2D:BA:62:51:F5:EC:71:FE:99:A8:D9:72:A4:6F:A4:DC:08:E5
         Nom de l'algorithme de signature : SHA256withRSA
         Version : 3

Extensions :

#1: ObjectId: 2.5.29.14 Criticality=false
SubjectKeyIdentifier [
KeyIdentifier [
0000: DE D5 A9 2F D6 EB EF 53   B2 68 7D A4 71 1B 55 B4  .../...S.h..q.U.
0010: CC 78 B5 10                                        .x..
]
]
