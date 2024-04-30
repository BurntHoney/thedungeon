import java.util.ArrayList;

public class Room {

    private boolean visited = false;
    private Enemy[] enemies;
    private String[] loot;
    private Boolean locked;
    private String lockItem;
    public String code;

    Room(String code){
        this.code = code;
        this.enemies = new Enemy[0];
    }

    Room(String code, Boolean locked, String lockItem) {
        this.locked = locked;
        this.lockItem = lockItem;
        this.code = code;
        this.enemies = new Enemy[0];
    }

    Room(String code, String[] loot){
        this.loot = loot;
        this.code = code;
        this.enemies = new Enemy[0];
    }

    Room(String code, Enemy[] enemies){
        this.enemies = enemies;
        this.code = code;
    }


    public boolean isVisited() {return this.visited;}
    public void visit(){this.visited = true;}

    public void clearEnemies(){this.enemies = null;}
    public Enemy[] getEnemies(){return this.enemies;}

    public String[] getLoot(){return this.loot;}
    public void clearLoot(){this.loot = null;}

    public Boolean isLocked(){return this.locked;}
    public boolean unlock(ArrayList<String> inventory){
        if (inventory.contains(this.lockItem)) {
            this.code = "u";
        };
        return this.locked;
    }
}
