import java.util.ArrayList;

public class Player {
    String name;

    private int maxHealth;
    private int health;
    private int damage;

    boolean isDead = false;
    ArrayList<String> inventory = new ArrayList<>();


    Player(String name, int health, int damage){
        this.name = name;
        this.maxHealth = health;
        this.health = health;
        this.damage = damage;
    }


    public void consume(String item){
        // Exit if the item does not exist
        if (!this.inventory.contains(item)){
            return;
        }

        // Consume the item
        this.inventory.remove(item);

        // key_1 and key_2 do not do anything over here, so we ignore it
        // based on the item we do things
        switch (item) {
            case "lesser_potion":
                this.heal(5);
                break;
            case "greater_potion":
                this.heal(10);
                break;
            case "essence":
                this.maxHealth += 5;
            case "bezerker_blood":
                this.damage += 1;
        }
    }

    public int getHealth(){return this.health;}
    public int getDamage(){
        int modifiers = 0;
        if (this.inventory.contains("sword")) modifiers+= 5;
        return this.damage + modifiers;
    }

    public void heal(int amount){
        this.health += amount;

        if (this.health > this.maxHealth) this.health = this.maxHealth;

    }

    public void takeDamage(int damage){
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            this.isDead = true;
        }
    }

}
