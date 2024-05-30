/**
 * The Dungeon class contains everything related to the dungeon and helps the player travers and battle
 */
public class Dungeon {

    Room[][] rooms = new Room[5][5];

    int xPos = 0;
    int yPos = 2;

    public Dungeon() {
        // Create the dungeon with rooms and enemies inside of them

        this.rooms[0][0] = new Room(" ");
        this.rooms[0][1] = new Room(" ");
        this.rooms[0][2] = new Room(" ");
        this.rooms[0][3] = new Room(" ");
        this.rooms[0][4] = new Room(" ");

        Character goblin = new Character();
        goblin.setName("Goblin");
        goblin.setMaxHealth(5);
        goblin.setDamage(1);
        goblin.inventory.add("lesser_healing_potion");
        this.rooms[0][0].enemies.add(goblin);
        this.rooms[0][0].visitMessage.add(
                "goblin:ooohhhh a pray to feast onnnnnn"
            );
        this.rooms[0][0].visitMessage.add(
                "system:use the command attack <index> where index is the position of the enemy starting from 0. In this case attack the goblin by using the command attack 0"
            );

        this.rooms[0][1].visitMessage.add(
                "system:the goblin seem's to have dropped a potion, trying using it by using the command use lesser_healing_potion and see what it does"
            );

        this.rooms[0][2].visitMessage.add(
                "system:there seems to be a locked door over there, trying exploring a bit more to find a key"
            );

        Character skeleton = new Character();
        skeleton.inventory.add("milk");
        skeleton.inventory.add("greater_healing_potion");
        skeleton.setDamage(2);
        skeleton.setMaxHealth(7);
        skeleton.setName("skeleton");
        this.rooms[0][3].enemies.add(skeleton);
        this.rooms[0][3].visitMessage.add(
                "skeleton:looks like that dingus goblin wasn't able to beat you. I shall crush you with my high calcium bones"
            );

        this.rooms[1][0] = new Room(" ");
        this.rooms[1][1] = new Room("w");
        this.rooms[1][2] = new Room("l", "gate_key");
        this.rooms[1][3] = new Room("w");
        this.rooms[1][4] = new Room("m");

        Character minotaur = new Character();
        minotaur.setName("minotaur");
        minotaur.setMaxHealth(20);
        minotaur.setDamage(3);
        minotaur.inventory.add("gate_key");
        minotaur.inventory.add("protein");
        this.rooms[1][4].enemies.add(minotaur);
        this.rooms[1][4].visitMessage.add("minotaur:get a taste of my muscles");

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

        Character dragon = new Character();
        dragon.setName("dragon");
        dragon.setMaxHealth(80);
        dragon.setDamage(7);
        dragon.inventory.add("dungeon_key");
        this.rooms[3][4].enemies.add(dragon);
        this.rooms[3][4].visitMessage.add(
                "dragon:if you couldn't beat me before then you can't beat me now"
            );

        this.rooms[4][0] = new Room(" ");
        this.rooms[4][1] = new Room(" ");
        this.rooms[4][2] = new Room(" ");
        this.rooms[4][3] = new Room("w");
        this.rooms[4][4] = new Room("l", "dungeon_key");

        Character djinn_1 = new Character();
        djinn_1.setName("djinn");
        djinn_1.setDamage(3);
        djinn_1.setMaxHealth(20);
        djinn_1.inventory.add("greater_healing_potion");

        Character djinn_2 = new Character();
        djinn_2.setName("djinn");
        djinn_2.setDamage(3);
        djinn_2.setMaxHealth(20);
        djinn_2.inventory.add("greater_healing_potion");
        djinn_2.inventory.add("steroids");

        this.rooms[4][0].enemies.add(djinn_1);
        this.rooms[4][0].enemies.add(djinn_2);
        this.rooms[4][0].visitMessage.add("djinn 1:ooh it seem's you found us");
        this.rooms[4][0].visitMessage.add(
                "djinn 2:honestly only one of us is required"
            );

        Character babyDragon = new Character();
        babyDragon.setName("baby dragon");
        babyDragon.setDamage(5);
        babyDragon.setMaxHealth(15);
        babyDragon.inventory.add("steroids");
        this.rooms[4][2].enemies.add(babyDragon);
        this.rooms[4][2].visitMessage.add(
                "baby dragon:bwaaahahahahahahahahah burppp...."
            );

        // Make sure to reveal all the postions
        revealBounds();
    }

    /**
     * Move the player in a certain direction
     * @param direction
     * @return
     */
    public int movePlayer(String direction) {
        if (Main.isInBattle) {
            Console.log("system", "you cannot flee from battle, coward!!!");
            return 1;
        }

        int xOffset = 0, yOffset = 0;

        switch (direction) {
            case "n":
                yOffset--;
                break;
            case "s":
                yOffset++;
                break;
            case "e":
                xOffset++;
                break;
            case "w":
                xOffset--;
                break;
            default:
                return 1;
        }

        if (
            this.xPos + xOffset < 0 ||
            this.xPos + xOffset > 4 ||
            this.yPos + yOffset < 0 ||
            this.yPos + yOffset > 4
        ) {
            Console.log("Sytem", "that position is out of bounds!");
            return 1;
        }

        Room currentRoom = rooms[yPos + yOffset][xPos + xOffset];
        if (currentRoom.getCode() == "w") {
            return 1;
        }

        if (currentRoom.getCode() == "l") {
            currentRoom.unlock();
            if (currentRoom.isLocked()) {
                return 1;
            }
        }

        if (!currentRoom.enemies.isEmpty()) Main.isInBattle = true;
        currentRoom.enter();
        Main.enteredRoom = true;

        xPos += xOffset;
        yPos += yOffset;

        if (xPos == 4 && yPos == 4) {
            Console.log(
                "system",
                "congrat's for escaping the dungeon. You win abosolutely nothing :)"
            );
            Main.isGameOver = true;
        }

        revealBounds();
        return 0;
    }

    /**
     * Reveals the surrounding rooms on the map
     */
    public void revealBounds() {
        revealIfInBound(yPos + 1, xPos);
        revealIfInBound(yPos - 1, xPos);
        revealIfInBound(yPos, xPos + 1);
        revealIfInBound(yPos, xPos - 1);
        revealIfInBound(yPos + 1, xPos + 1);
        revealIfInBound(yPos + 1, xPos - 1);
        revealIfInBound(yPos - 1, xPos + 1);
        revealIfInBound(yPos - 1, xPos - 1);
    }

    /**
     * Reveals the room if the position is inside the dungeon
     * @param yPos
     * @param xPos
     */
    private void revealIfInBound(int yPos, int xPos) {
        if (
            yPos >= 0 && yPos <= 4 && xPos >= 0 && xPos <= 4
        ) rooms[yPos][xPos].reveal();
    }

    /**
     * Allows the enemies to attack the player
     */
    public void attackPlayer() {
        Room currentRoom = rooms[yPos][xPos];
        if (currentRoom.enemies.isEmpty()) return;
        for (Character enemy : currentRoom.enemies) {
            int damage = enemy.getDamage();
            Main.player.takeDamage(damage);
            Console.log(
                "system",
                String.format(
                    "%s dealt %d damage to you!",
                    enemy.getName(),
                    damage
                )
            );
        }

        if (Main.player.getHealth() == 0) {
            Main.isGameOver = true;
            Console.log("system", "You have been slain");
        }
    }

    /**
     * Attack an enemy
     * @param position the position of the enemy
     * @return int the error code
     */
    public int attackEnemy(String position) {
        if (!Main.isInBattle) {
            Console.log(
                "system",
                "Who are you waving your weapon at? There are no enemies over here"
            );

            return -1;
        }

        int enemyPosition;
        try {
            enemyPosition = Integer.valueOf(position);
        } catch (NumberFormatException e) {
            Console.log(
                "system",
                "please specify the target's position to attack"
            );
            return -1;
        }

        Room currentRoom = rooms[yPos][xPos];
        if (enemyPosition < 0 || enemyPosition > currentRoom.enemies.size()) {
            Console.log(
                "system",
                "the target at the specified position does not exist"
            );
            return 1;
        }

        Character enemy = currentRoom.enemies.get(enemyPosition);
        int damage = Main.player.getDamage();
        Console.log(
            "System",
            String.format(
                "you attacked %s for %d damage!",
                enemy.getName(),
                damage
            )
        );
        enemy.takeDamage(damage);
        if (enemy.getHealth() == 0) {
            Console.log(
                "system",
                String.format("You have slain %s!", enemy.getName())
            );
            Main.player.inventory.addAll(enemy.inventory);
            rooms[yPos][xPos].enemies.remove(enemy);
            Console.log("debug", String.valueOf(currentRoom.enemies.size()));
            if (currentRoom.enemies.isEmpty()) Main.isInBattle = false;
        }

        return 0;
    }
}
