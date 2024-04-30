public class Main {
    public static Display display = new Display();
    public static Game game = new Game();
    public static Player player = new Player();

    public static void main(String[] args) {
        display.display();

        display.printLine("SYSTEM","What is your name?");
        display.printLine("PLAYER", display.readLine("Name: "));

        while (!game.getGameOver()){
            display.display();
            game.gameLoop();
        }

        display.close();
    }
}