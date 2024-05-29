public class Dungeon {
    Room[][] rooms = new Room[5][5];

    int xPos = 0;
    int yPos = 3;

    public Dungeon() {
        this.rooms[0][0] = new Room("w");
        this.rooms[0][1] = new Room("w");
        this.rooms[0][2] = new Room("w");
        this.rooms[0][3] = new Room("w");
        this.rooms[0][4] = new Room("w");

        this.rooms[1][0] = new Room("w");
        this.rooms[1][1] = new Room("w");
        this.rooms[1][2] = new Room("w");
        this.rooms[1][3] = new Room("w");
        this.rooms[1][4] = new Room("w");

        this.rooms[2][0] = new Room("w");
        this.rooms[2][1] = new Room("w");
        this.rooms[2][2] = new Room("w");
        this.rooms[2][3] = new Room("w");
        this.rooms[2][4] = new Room("w");

        this.rooms[3][0] = new Room("w");
        this.rooms[3][1] = new Room("w");
        this.rooms[3][2] = new Room("w");
        this.rooms[3][3] = new Room("w");
        this.rooms[3][4] = new Room("w");

        this.rooms[4][0] = new Room("w");
        this.rooms[4][1] = new Room("w");
        this.rooms[4][2] = new Room("w");
        this.rooms[4][3] = new Room("w");
        this.rooms[4][4] = new Room("w");
    }

    public void movePlayer(String direction) {

    }
}
