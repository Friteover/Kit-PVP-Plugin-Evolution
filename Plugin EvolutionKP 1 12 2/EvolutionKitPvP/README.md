# 🎮 Evolution Kit PvP - Plugin Minecraft 1.12.2

Un plugin Kit PvP **unique** avec un système d'évolution progressive ! Les joueurs évoluent à travers 7 kits différents en tuant des adversaires.

## 🌟 Concept

**Système d'Évolution** :
- 🪵 Commencez au niveau 1 avec un kit basique
- ⚔️ Tuez des joueurs pour progresser vers le kit suivant
- 💀 Mourir vous fait redescendre d'un niveau
- 👑 Atteignez le niveau 7 pour devenir une Légende

## 📋 Les 7 Kits

| Niveau | Nom | Équipement | Spécial |
|--------|-----|------------|---------|
| 1 | 🪵 Débutant | Épée bois, Armure cuir | - |
| 2 | ⛏️ Mineur | Épée pierre, Casque fer + Armure cuir | - |
| 3 | ⚔️ Guerrier | Épée fer, Armure fer | Sharpness I |
| 4 | 💎 Champion | Épée diamant, Armure diamant | Sharpness II, Protection I |
| 5 | 🔥 Pyromancien | Épée diamant Fire Aspect II | Résistance au feu, Fire Charges |
| 6 | ⚡ Assassin | Épée diamant Sharpness V | Speed II, Ender Pearls |
| 7 | 👑 Légende | Équipement diamant maximal | Totem, Regen, Speed, Enchants max |

## 🔧 Installation

### Prérequis
- **Serveur Minecraft 1.12.2** (Spigot/Paper)
- **Java 8** installé
- **Maven** pour compiler (optionnel)

### Méthode 1 : Avec Maven (recommandé)

```bash
# Cloner ou télécharger le projet
cd EvolutionKitPvP

# Compiler le plugin
mvn clean package

# Le fichier JAR sera dans target/EvolutionKitPvP-1.0.jar
```

### Méthode 2 : Sans Maven

Si Maven n'est pas installé, vous pouvez :
1. Installer Maven : https://maven.apache.org/download.cgi
2. Ou utiliser un IDE comme IntelliJ IDEA ou Eclipse qui compile automatiquement

### Installation sur le serveur

1. Copiez `EvolutionKitPvP-1.0.jar` dans le dossier `plugins/` de votre serveur
2. Redémarrez le serveur
3. Le plugin créera automatiquement un fichier `config.yml`

## 🎯 Configuration

### Première utilisation

1. **Définir le spawn de l'arène** :
   ```
   /kitpvp setspawn
   ```
   (Placez-vous à l'endroit où vous voulez que les joueurs apparaissent)

2. **Personnaliser la configuration** (optionnel) :
   Éditez `plugins/EvolutionKitPvP/config.yml`

### Options de configuration

```yaml
# Kills requis pour passer au niveau suivant
kills-per-level:
  1: 1  # Débutant -> Mineur (1 kill)
  2: 2  # Mineur -> Guerrier (2 kills)
  3: 3  # Guerrier -> Champion (3 kills)
  4: 5  # Champion -> Pyromancien (5 kills)
  5: 7  # Pyromancien -> Assassin (7 kills)
  6: 10 # Assassin -> Légende (10 kills)

# Activer/désactiver des fonctionnalités
features:
  scoreboard: true      # Afficher le scoreboard
  killstreaks: true     # Messages de killstreak
  death-messages: true  # Messages de mort
  level-regression: true # Perdre un niveau en mourant
```

## 🕹️ Commandes

| Commande | Description | Permission |
|----------|-------------|------------|
| `/kitpvp join` | Rejoindre l'arène | `evolutionkit.join` |
| `/kitpvp leave` | Quitter l'arène | `evolutionkit.leave` |
| `/kitpvp stats [joueur]` | Voir les statistiques | `evolutionkit.stats` |
| `/kitpvp reset` | Reset ses stats (admin) | `evolutionkit.admin` |
| `/kitpvp setspawn` | Définir le spawn (admin) | `evolutionkit.admin` |

**Alias** : `/kp` ou `/kit`

## 🎮 Comment jouer

1. **Rejoindre l'arène** :
   ```
   /kitpvp join
   ```

2. **Combattre** :
   - Vous commencez au niveau 1 (Débutant)
   - Tuez des joueurs pour progresser
   - Chaque kill vous rapproche du prochain kit
   - Mourir vous fait redescendre d'un niveau

3. **Killstreaks** :
   - 3 kills : 🔥 Killstreak x3
   - 5 kills : 🔥 Killstreak x5 - Domination !
   - 10 kills : 🔥 KILLSTREAK x10 - UNSTOPPABLE !

4. **Scoreboard** :
   Le scoreboard affiche en temps réel :
   - Votre kit actuel
   - Niveau (1-7)
   - Kills / Morts / K/D Ratio
   - Killstreak actuel

## 📊 Permissions

```yaml
evolutionkit.join: true      # Rejoindre l'arène (par défaut)
evolutionkit.leave: true     # Quitter l'arène (par défaut)
evolutionkit.stats: true     # Voir les stats (par défaut)
evolutionkit.admin: op       # Commandes admin (OP uniquement)
```

## 🛠️ Support

**Version Minecraft** : 1.12.2  
**API** : Spigot/Paper 1.12.2  
**Java** : 8

## 📝 Notes

- Les items ne sont **pas droppés** à la mort
- Les joueurs respawnent automatiquement dans l'arène
- Les statistiques sont **sauvegardées en mémoire** (reset au redémarrage du serveur)
- Le plugin est **standalone**, aucune dépendance externe requise

## 🎨 Personnalisation

Tous les messages sont personnalisables dans `config.yml` :

```yaml
messages:
  join: "&a✓ Vous avez rejoint l'arène !"
  kill: "&a+1 Kill ! &7Progression: &e{progress}%"
  level-up: "&6⚡ LEVEL UP ! &7Nouveau kit: &e{kit}"
  # ... et plus encore
```

---

**Bon jeu ! 🎮⚔️**
