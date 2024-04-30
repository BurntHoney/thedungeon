public class Enemy {
    private final String name;
    private int health;
    private final int damage;

    Enemy(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
    }

    public String getName() {return this.name;}
    public int getHealth() {return this.health;}

    public int takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
        }
        return this.health;
    }

    public int getDamage(){return this.damage;}
}
