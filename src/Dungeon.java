public class Dungeon {

    Room[][] rooms = new Room[5][5];

    int xPos = 0;
    int yPos = 0;

    public Dungeon() {
        // boss
        Character dragon = new Character();
        dragon.setName("dragon");

        // miniboss
        Character minotaur = new Character();
        minotaur.setName("minotaur");

        // Gobins
        Character grodax = new Character();
        grodax.setName("grodax");

        Character dalek = new Character();
        dalek.setName("dalek");

        // Rooms
        this.rooms[0][0] = new Room(" ");
        this.rooms[0][1] = new Room(" ");
        this.rooms[0][2] = new Room(" ");
        this.rooms[0][3] = new Room(" ");
        this.rooms[0][4] = new Room(" ");

        // Add Enemies to rooms
        this.rooms[0][0].enemies.add(grodax);
        this.rooms[0][0].enemies.add(dalek);

        this.rooms[1][0] = new Room(" ");
        this.rooms[1][1] = new Room("w");
        this.rooms[1][2] = new Room("l", "gate_key");
        this.rooms[1][3] = new Room("w");
        this.rooms[1][4] = new Room("m");

        this.rooms[2][0] = new Room(" ");
        this.rooms[2][1] = new Room("w");
        this.rooms[2][2] = new Room(" ");
        this.rooms[2][3] = new Room("w");
        this.rooms[2][4] = new Room("w");

        this.rooms[3][0] = new Room("w");
        this.rooms[3][1] = new Room("w");
        this.rooms[3][2] = new Room(" ");
        this.rooms[3][3] = new Room(" ");
        this.rooms[3][4] = new Room("b");

        this.rooms[4][0] = new Room("t");
        this.rooms[4][1] = new Room(" ");
        this.rooms[4][2] = new Room(" ");
        this.rooms[4][3] = new Room("w");
        this.rooms[4][4] = new Room("l", "dungeon_key");
    }

    public int movePlayer(String direction) {
        int xOffset = 0, yOffset = 0;

        switch (direction) {
            case "n":
            case "north":
                yOffset--;
                break;
            case "s":
            case "south":
                yOffset++;
                break;
            case "e":
            case "east":
                xOffset++;
                break;
            case "w":
            case "west":
                xOffset--;
                break;
            default:
                return 1;
        }

        if (this.xPos + xOffset < 0 || this.xPos + xOffset > 4) {
            return 1;
        }

        if (this.yPos + xOffset < 0 || this.yPos + xOffset > 4) {
            return 1;
        }

        Room currentRoom = rooms[yPos + yOffset][xPos + xOffset];
        if (currentRoom.code == "w") {
            return 1;
        }

        if (currentRoom.code == "l") {
            currentRoom.unlock(Main.player.inventory);
            if (currentRoom.isLocked()) {
                return 1;
            }
        }

        xPos += xOffset;
        yPos += yPos;

        return 0;
    }
}
