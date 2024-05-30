import java.util.ArrayList;

public class Room {

    private boolean revealed = false;
    private String code;
    private String lockItem = null;

    private boolean visited = false;
    public ArrayList<String> visitMessage = new ArrayList<>();

    public ArrayList<Character> enemies = new ArrayList<>();
    public ArrayList<String> loot = new ArrayList<>();

    Room(String code) {
        this.code = code;
    }

    Room(String code, String lockItem) {
        this.code = code;
        this.lockItem = lockItem;
    }

    /**
     * reveal a room on the minimap
     */
    public void reveal() {
        this.revealed = true;
    }

    /**
     * Returns the code or - if the room has not been revealed
     * @return String represents the type of type
     */
    public String getCode() {
        if (!revealed) return "-";
        return this.code;
    }

    /**
     * Enter a room triggering any story lines
     */
    public void enter() {
        if (!this.visited) {
            for (String message : visitMessage) {
                String[] message_parts = message.split(":");
                Console.log(message_parts[0], message_parts[1]);
            }
            this.visited = true;
        }
    }

    /**
     * Checks whether a room is locked or unlocked
     * @return boolean
     */
    public boolean isLocked() {
        return this.code.equals("l");
    }

    /**
     * Unlocks the room and consumes the key in the player's inventory
     * @return
     */
    public boolean unlock() {
        if (Main.player.inventory.contains(this.lockItem)) {
            this.code = " ";
        }

        return this.code.equals("l");
    }
}
