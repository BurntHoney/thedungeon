import java.util.ArrayList;

public class Character {

    private String name;

    public int maxHealth = 10;
    public int currentHealth = 10;
    public int defence = 0;
    public int damage = 1;

    ArrayList<String> imageBuffer = new ArrayList<>();

    ArrayList<Item> inventory = new ArrayList<Item>();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getHealth() {
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
}
