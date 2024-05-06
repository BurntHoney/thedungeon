import java.util.ArrayList;

public class Player {
    public String name;
    public int maxHealth = 10;
    public int health = 10;
    public int damage = 4;
    public int defence = 0;

    public ArrayList<String> inventory = new ArrayList<>();

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
        }
    }

    private void heal(int health) {
        this.health += health;
        if (this.health > maxHealth) {
            this.health = maxHealth;
        }
    }

    public boolean useItem(String item) {
        if (!inventory.contains(item)) {
            Main.display.printLine("system", "you do not have this item");
            return false;
        }
        inventory.remove(item);

        switch (item){
            case "lesser_potion":
                this.heal(5);
                break;
            case "greater_potion":
                this.heal(10);
                break;
            case "milk":
                this.defence += 1;
            case "supreme_milk":
                this.defence += 3;
            default:
                Main.display.printLine("system", "this item is not consumable");
                return false;
        }

        return true;
    }
}
