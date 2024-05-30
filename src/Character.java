import java.util.ArrayList;
import java.util.Random;

/**
 * Character class that represents the player and enemies
 */
public class Character {

    private String name;

    private int maxHealth = 10;
    private int currentHealth = 10;
    private int defence = 0;
    private int damage = 1;
    private float critRate = 0.1f;

    ArrayList<String> imageBuffer = new ArrayList<>();

    ArrayList<String> inventory = new ArrayList<>();

    /**
     * set the name of the character
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the name of the character
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * get the character's health
     * @return
     */
    public int getHealth() {
        return this.currentHealth;
    }

    /**
     * get the character's maximum health
     * @param health
     */
    public void setMaxHealth(int health) {
        this.maxHealth = health;
        this.currentHealth = health;
    }

    /**
     * get's the maximum health for the character
     * @return int
     */
    public int getMaxHealth() {
        return this.maxHealth;
    }

    /**
     * getDamage returns the amount of damage to deal, if the user crit's the damage is multiplied by 2
     * @return int the amount of damage to deal
     */
    public int getDamage() {
        Random random = new Random();
        if (random.nextFloat(0, 1) <= critRate) return this.damage * 2;
        return this.damage;
    }

    /**
     * Get's the raw damage without any modifiers
     * @return int
     */
    public int getRawDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Get's the defence of the character
     * @return int
     */
    public int getDefence() {
        return this.defence;
    }

    /**
     * takeDamage calculates the damage the user is supposed to take after taking into account of the user's defence
     * @param damage the amount of damage to deal
     */
    public void takeDamage(int damage) {
        damage -= defence;

        if (damage <= 0) return;

        this.currentHealth -= damage;
        if (this.currentHealth < 0) {
            this.currentHealth = 0;
        }
    }

    /**
     * Heal the player for a certain amount which is capped at the max health
     * @param health
     */
    public void heal(int health) {
        this.currentHealth += health;
        if (this.currentHealth > this.maxHealth) {
            this.currentHealth = this.maxHealth;
        }
    }

    /**
     * use an item and apply the affect's to the character
     * @param itemName
     * @return int error code
     */
    public int useItem(String itemName) {
        if (itemName == "") {
            Console.log("system", "please provide the name of the item");
            return 1;
        }

        if (!inventory.contains(itemName)) {
            Console.log(
                "System",
                String.format("%s is not in your inventory", itemName)
            );
            return 1;
        }

        inventory.remove(itemName);
        switch (itemName) {
            case "milk":
                this.defence += 2;
                break;
            case "lesser_healing_potion":
                this.heal(5);
                break;
            case "greater_healing_potion":
                this.heal(10);
                break;
            case "protein":
                this.damage += 2;
                break;
            case "steroids":
                this.damage += 3;
                this.maxHealth += 4;
                this.currentHealth = maxHealth;
                this.critRate *= 3;
                break;
        }

        return 0;
    }
}
