public class Main {
    public static Display display = new Display();
    public static Game game = new Game();
    public static Player player = new Player();

    public static void main(String[] args) {
        // Get Player Name
        display.printLine("system","What is your name?");

        String playerName = display.readLine("Name: ");
        display.printLine("player", playerName);
        display.printLine("system", String.format("welcome to the dungeon %s", playerName));

        // Game Loop
        while (!game.getGameOver()){
            display.display();
            game.gameLoop();
        }

        display.close();
    }
}