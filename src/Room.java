import java.util.ArrayList;

public class Room {
    public String code;
    private String lockItem = null;

    private boolean visited = false;
    public ArrayList<Enemy> enemies = new ArrayList<>();
    public ArrayList<String> loot = new ArrayList<>();




    Room(String code){
        this.code = code;
    }

    Room(String code, String lockItem){
        this.code = code;
        this.lockItem = lockItem;
    }

    public boolean isVisited() {return this.visited;}
    public void visit(){this.visited = true;}


    public Boolean isLocked(){return this.code.equals("l");}
    public boolean unlock(ArrayList<String> inventory){
        if (inventory.contains(this.lockItem)) {
            this.code = " ";
        };
        return this.code.equals("l");
    }
}
