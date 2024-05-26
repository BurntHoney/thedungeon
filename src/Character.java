import java.util.ArrayList;

public class Character {
    private String name;

    private int maxHealth;
    private int currentHealth;
    private int defence;
    private int damage;

    ArrayList<Item> inventory = new ArrayList<Item>();

    /**
     * Set the name of the character
     * 
     * @param name the name of the character to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getHealth(int health) {
        return this.currentHealth;
    }

    public void setHealth(int health) {
        this.maxHealth = health;
        this.currentHealth = health;
    }

    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void takeDamage(int damage) {
        damage -= defence;

        if (damage <= 0)
            return;

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

}
