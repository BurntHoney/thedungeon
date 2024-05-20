public class Main {
    public static Display display = new Display();
    public static Game game = new Game();
    public static Player player = new Player();

    public static void main(String[] args) {
        Main.display.console.log("system", "What is your name?", "info");
        Main.display.display();

        String playerName = display.readLine("Name: ");
        Main.display.console.log("player", playerName, "spam");
        Main.display.console.log("system", String.format("welcome to the dungeon %s", playerName), "info");

        // Game Loop
        while (!game.getGameOver()){
            display.display();
            game.gameLoop();
        }

        display.close();
    }
}