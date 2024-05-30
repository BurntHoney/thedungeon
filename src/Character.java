import java.util.ArrayList;
import java.util.Random;

public class Character {

    private String name;

    public int maxHealth = 10;
    public int currentHealth = 10;
    public int defence = 0;
    public int damage = 1;
    public float critRate = 0.1f;

    ArrayList<String> imageBuffer = new ArrayList<>();

    ArrayList<String> inventory = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getHealth() {
        return this.currentHealth;
    }

    public void setMaxHealth(int health) {
        this.maxHealth = health;
        this.currentHealth = health;
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

    public int getRawDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
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

    public void heal(int health) {
        this.currentHealth += health;
        if (this.currentHealth > this.maxHealth) {
            this.currentHealth = this.maxHealth;
        }
    }

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
                Console.log("debug", "" + Main.player.getHealth());
                this.heal(5);
                break;
            case "greater_healing_potion":
                this.heal(10);
                break;
        }

        return 0;
    }
}
