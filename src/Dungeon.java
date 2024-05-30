public class Dungeon {

    Room[][] rooms = new Room[5][5];

    int xPos = 0;
    int yPos = 2;

    public Dungeon() {
        // Items
        Item gateKey = new Item("gate_key");
        Item dungeonKey = new Item("dungeon_key");
        Item lesserHealthPotion = new Item("lesser_health_potion");
        Item greaterHealthPotion = new Item("greater_health_potion");
        Item milk = new Item("milk");

        // boss
        Character dragon = new Character();
        dragon.setName("dragon");
        dragon.setHealth(100);
        dragon.setDamage(10);
        dragon.inventory.add(dungeonKey);

        // miniboss
        Character minotaur = new Character();
        minotaur.setName("minotaur");
        minotaur.setHealth(50);
        minotaur.setDamage(5);
        minotaur.inventory.add(gateKey);

        // Gobins
        Character goblin = new Character();
        goblin.setName("Goblin");
        goblin.setHealth(5);
        goblin.setDamage(1);
        goblin.inventory.add(lesserHealthPotion);

        // Rooms
        this.rooms[0][0] = new Room(" ");
        this.rooms[0][1] = new Room(" ");
        this.rooms[0][2] = new Room(" ");
        this.rooms[0][3] = new Room(" ");
        this.rooms[0][4] = new Room(" ");

        // Add Enemies to rooms
        this.rooms[0][0].enemies.add(goblin);

        this.rooms[1][0] = new Room(" ");
        this.rooms[1][1] = new Room("w");
        this.rooms[1][2] = new Room("l", gateKey);
        this.rooms[1][3] = new Room("w");
        this.rooms[1][4] = new Room("m");

        this.rooms[1][0].enemies.add(goblin);
        this.rooms[1][4].enemies.add(minotaur);

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
        this.rooms[4][4] = new Room("l", dungeonKey);

        // Make sure to reveal all the postions
        revealIfInBound(yPos, xPos);
        revealIfInBound(yPos + 1, xPos);
        revealIfInBound(yPos - 1, xPos);
        revealIfInBound(yPos, xPos + 1);
        revealIfInBound(yPos, xPos - 1);
        revealIfInBound(yPos + 1, xPos + 1);
        revealIfInBound(yPos + 1, xPos - 1);
        revealIfInBound(yPos - 1, xPos + 1);
        revealIfInBound(yPos - 1, xPos - 1);
    }

    public int movePlayer(String direction) {
        if (Main.isInBattle) {
            Console.log("system", "you cannot flee from battle, coward!!!");
            return 1;
        }

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
            currentRoom.unlock(Main.player.inventory);
            if (currentRoom.isLocked()) {
                return 1;
            }
        }

        if (!currentRoom.enemies.isEmpty()) Main.isInBattle = true;
        currentRoom.enter();
        Main.enteredRoom = true;

        xPos += xOffset;
        yPos += yOffset;

        // Reveal new parts of the map
        revealIfInBound(yPos + 1, xPos);
        revealIfInBound(yPos - 1, xPos);
        revealIfInBound(yPos, xPos + 1);
        revealIfInBound(yPos, xPos - 1);
        revealIfInBound(yPos + 1, xPos + 1);
        revealIfInBound(yPos + 1, xPos - 1);
        revealIfInBound(yPos - 1, xPos + 1);
        revealIfInBound(yPos - 1, xPos - 1);

        return 0;
    }

    private void revealIfInBound(int yPos, int xPos) {
        if (
            yPos >= 0 && yPos <= 4 && xPos >= 0 && xPos <= 4
        ) rooms[yPos][xPos].reveal();
    }

    public void simulateBattle() {
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
