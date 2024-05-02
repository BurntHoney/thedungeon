import java.util.ArrayList;

public class Player {
    public String name;
    public int maxHealth = 10;
    public int health = 10;
    public int damage = 4;

    public ArrayList<String> inventory = new ArrayList<>();

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
        }
    }
}
