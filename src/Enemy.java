import java.util.ArrayList;

public class Enemy {
    public final String name;
    public int health;
    public final int damage;
    public String deathLine;

    public ArrayList<String> inventory = new ArrayList<>();

    Enemy(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
    }

    Enemy(String name, int health, int damage, String deathLine) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.deathLine = deathLine;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            if (this.deathLine != null)
                Main.display.printLine(this.name, deathLine);
        }
    }
}
