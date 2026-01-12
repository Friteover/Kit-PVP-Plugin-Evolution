# 🔨 Guide de Compilation - Evolution Kit PvP

## ❌ Problème : Maven n'est pas installé

Maven n'est pas disponible sur votre système. Voici **3 solutions** pour compiler le plugin :

---

## ✅ Solution 1 : Installer Maven (Recommandé)

### Étapes d'installation

1. **Télécharger Maven**
   - Allez sur : https://maven.apache.org/download.cgi
   - Téléchargez `apache-maven-3.x.x-bin.zip`

2. **Extraire l'archive**
   - Extrayez dans `C:\Program Files\Apache\maven`

3. **Ajouter au PATH**
   - Ouvrez les variables d'environnement Windows
   - Ajoutez `C:\Program Files\Apache\maven\bin` au PATH
   - Redémarrez PowerShell

4. **Vérifier l'installation**
   ```powershell
   mvn --version
   ```

5. **Compiler le plugin**
   ```powershell
   cd "C:\Users\Tom\Desktop\Nouveau dossier (3)\EvolutionKitPvP"
   mvn clean package
   ```

Le fichier JAR sera dans `target\EvolutionKitPvP-1.0.jar`

---

## ✅ Solution 2 : Utiliser IntelliJ IDEA (Plus Simple)

### Installation

1. **Télécharger IntelliJ IDEA Community** (gratuit)
   - https://www.jetbrains.com/idea/download/

2. **Ouvrir le projet**
   - Lancez IntelliJ IDEA
   - `File` → `Open`
   - Sélectionnez le dossier `EvolutionKitPvP`
   - IntelliJ détectera automatiquement le projet Maven

3. **Compiler**
   - Attendez que IntelliJ télécharge les dépendances
   - Clic droit sur `pom.xml` → `Maven` → `Reload Project`
   - Ouvrez l'onglet `Maven` (à droite)
   - Double-cliquez sur `Lifecycle` → `package`

Le JAR sera dans `target\EvolutionKitPvP-1.0.jar`

---

## ✅ Solution 3 : Utiliser Eclipse

### Installation

1. **Télécharger Eclipse IDE for Java Developers**
   - https://www.eclipse.org/downloads/

2. **Importer le projet**
   - `File` → `Import` → `Maven` → `Existing Maven Projects`
   - Sélectionnez le dossier `EvolutionKitPvP`
   - Cliquez sur `Finish`

3. **Compiler**
   - Clic droit sur le projet → `Run As` → `Maven build...`
   - Dans `Goals`, tapez : `clean package`
   - Cliquez sur `Run`

Le JAR sera dans `target\EvolutionKitPvP-1.0.jar`

---

## 📦 Après la Compilation

Une fois le JAR compilé :

1. **Localiser le fichier**
   ```
   EvolutionKitPvP\target\EvolutionKitPvP-1.0.jar
   ```

2. **Installer sur le serveur**
   - Copiez le JAR dans le dossier `plugins/` de votre serveur Minecraft 1.12.2
   - Redémarrez le serveur

3. **Configuration initiale**
   ```
   /kitpvp setspawn
   ```

4. **Tester**
   ```
   /kitpvp join
   ```

---

## 🆘 Dépannage

### Erreur : "Java version mismatch"
- Assurez-vous d'avoir **Java 8** installé
- Vérifiez avec : `java -version`

### Erreur : "Cannot resolve dependencies"
- Vérifiez votre connexion Internet
- Maven doit télécharger Spigot API 1.12.2

### Le JAR n'apparaît pas
- Vérifiez le dossier `target/`
- Regardez les logs de compilation pour les erreurs

---

## 💡 Recommandation

**Pour un débutant** : Utilisez **IntelliJ IDEA Community** (Solution 2)
- Gratuit
- Interface graphique simple
- Gère Maven automatiquement
- Pas besoin de configuration PATH

**Pour un développeur** : Installez **Maven** (Solution 1)
- Plus rapide
- Ligne de commande
- Utilisable pour tous les projets Java

---

**Besoin d'aide ?** Choisissez une solution et suivez les étapes ! 🚀
