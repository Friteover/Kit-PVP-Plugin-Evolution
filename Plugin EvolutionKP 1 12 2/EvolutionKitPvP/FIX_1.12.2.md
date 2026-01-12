# 🔧 Corrections pour Minecraft 1.12.2

## Erreurs de compilation détectées

Les matériaux suivants n'existent pas en 1.12.2 et doivent être remplacés :

### ❌ Erreur 1 : WOODEN_SWORD
**Ligne 13** dans `Kit.java`
```java
// AVANT (incorrect)
Material.WOODEN_SWORD

// APRÈS (correct pour 1.12.2)
Material.WOOD_SWORD
```

### ❌ Erreur 2 : FIRE_CHARGE  
**Ligne 134** dans `Kit.java`
```java
// AVANT (incorrect)
new ItemStack(Material.FIRE_CHARGE, 16)

// APRÈS (correct pour 1.12.2)
new ItemStack(Material.FIREBALL, 16)
```

### ❌ Erreur 3 : TOTEM_OF_UNDYING
**Ligne 144** dans `Kit.java`
```java
// AVANT (incorrect - le totem n'existe pas en 1.12.2)
new ItemStack(Material.TOTEM_OF_UNDYING, 1)

// APRÈS (correct pour 1.12.2)
new ItemStack(Material.GOLDEN_APPLE, 16)
```

Et aussi ligne 146 :
```java
// AVANT
new ItemStack(Material.GOLDEN_APPLE, 8)

// APRÈS  
new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 3)
```

---

## 📝 Solution Rapide : Copier le fichier corrigé

Voici le fichier `Kit.java` complet et corrigé pour 1.12.2 :

```java
package com.evolutionkit.kit;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public enum Kit {
    DEBUTANT(1, "§7Débutant", 
            Material.WOOD_SWORD, Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, 
            Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS),
    
    MINEUR(2, "§8Mineur", 
            Material.STONE_SWORD, Material.IRON_HELMET, Material.LEATHER_CHESTPLATE, 
            Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS),
    
    GUERRIER(3, "§fGuerrier", 
            Material.IRON_SWORD, Material.IRON_HELMET, Material.IRON_CHESTPLATE, 
            Material.IRON_LEGGINGS, Material.IRON_BOOTS),
    
    CHAMPION(4, "§bChampion", 
            Material.DIAMOND_SWORD, Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, 
            Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS),
    
    PYROMANCIEN(5, "§6Pyromancien", 
            Material.DIAMOND_SWORD, Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, 
            Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS),
    
    ASSASSIN(6, "§eAsassin", 
            Material.DIAMOND_SWORD, Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, 
            Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS),
    
    LEGENDE(7, "§5§lLégende", 
            Material.DIAMOND_SWORD, Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, 
            Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS);

    private final int level;
    private final String displayName;
    private final Material sword;
    private final Material helmet;
    private final Material chestplate;
    private final Material leggings;
    private final Material boots;

    Kit(int level, String displayName, Material sword, Material helmet, 
        Material chestplate, Material leggings, Material boots) {
        this.level = level;
        this.displayName = displayName;
        this.sword = sword;
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
    }

    public int getLevel() {
        return level;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void giveKit(Player player) {
        player.getInventory().clear();
        player.getActivePotionEffects().clear();
        
        // Épée
        ItemStack swordItem = new ItemStack(sword);
        ItemMeta swordMeta = swordItem.getItemMeta();
        
        switch (this) {
            case PYROMANCIEN:
                swordMeta.addEnchant(Enchantment.FIRE_ASPECT, 2, false);
                swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 3, false);
                break;
            case ASSASSIN:
                swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 5, false);
                swordMeta.addEnchant(Enchantment.KNOCKBACK, 1, false);
                break;
            case LEGENDE:
                swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 5, false);
                swordMeta.addEnchant(Enchantment.FIRE_ASPECT, 2, false);
                swordMeta.addEnchant(Enchantment.KNOCKBACK, 2, false);
                swordMeta.addEnchant(Enchantment.DURABILITY, 3, false);
                break;
            default:
                if (this.level >= 3) {
                    swordMeta.addEnchant(Enchantment.DAMAGE_ALL, this.level - 2, false);
                }
                break;
        }
        
        swordMeta.setDisplayName(displayName + " §r§7- Épée");
        swordItem.setItemMeta(swordMeta);
        player.getInventory().setItem(0, swordItem);
        
        // Armure
        ItemStack helmetItem = new ItemStack(helmet);
        ItemStack chestplateItem = new ItemStack(chestplate);
        ItemStack leggingsItem = new ItemStack(leggings);
        ItemStack bootsItem = new ItemStack(boots);
        
        // Enchantements armure
        if (this.level >= 4) {
            int protLevel = this.level - 3;
            helmetItem.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, protLevel);
            chestplateItem.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, protLevel);
            leggingsItem.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, protLevel);
            bootsItem.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, protLevel);
        }
        
        if (this == Kit.LEGENDE) {
            helmetItem.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
            chestplateItem.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
            leggingsItem.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
            bootsItem.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        }
        
        player.getInventory().setHelmet(helmetItem);
        player.getInventory().setChestplate(chestplateItem);
        player.getInventory().setLeggings(leggingsItem);
        player.getInventory().setBoots(bootsItem);
        
        // Nourriture
        player.getInventory().setItem(1, new ItemStack(Material.COOKED_BEEF, 64));
        
        // Items spéciaux selon le kit
        switch (this) {
            case PYROMANCIEN:
                player.getInventory().setItem(2, new ItemStack(Material.FIREBALL, 16));
                player.getInventory().setItem(3, new ItemStack(Material.LAVA_BUCKET, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, false, false));
                break;
            case ASSASSIN:
                player.getInventory().setItem(2, new ItemStack(Material.ENDER_PEARL, 8));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false));
                break;
            case LEGENDE:
                player.getInventory().setItem(2, new ItemStack(Material.GOLDEN_APPLE, 16));
                player.getInventory().setItem(3, new ItemStack(Material.ENDER_PEARL, 16));
                player.getInventory().setItem(4, new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 3));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0, false, false));
                break;
        }
        
        // Santé et nourriture
        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.setSaturation(20.0f);
    }

    public static Kit getByLevel(int level) {
        for (Kit kit : values()) {
            if (kit.level == level) {
                return kit;
            }
        }
        return DEBUTANT;
    }
}
```

---

## ✅ Instructions

1. **Ouvrez** le fichier `Kit.java` dans votre éditeur
2. **Remplacez** tout le contenu par le code ci-dessus
3. **Sauvegardez** le fichier
4. **Recompilez** avec `mvn clean package`

Ou faites les 3 modifications manuellement comme indiqué au début de ce fichier.
