public class Main {
    public static Display display = new Display();
    public static Game game = new Game();
    public static Player player = new Player();

    public static Character player2;

    // Enemies
    public static Character dragon;
    public static Character minotaur;

    // Component's
    public static MapComponent mapComponent = new MapComponent();

    public static void main(String[] args) {

        // player2 = new Character();
        // Main.display.console.log("system", "What is your name?", "info");
        // Main.display.display();

        // String playerName = display.readLine("Name: ");
        // Main.display.console.log("player", playerName, "spam");
        // Main.display.console.log("system", String.format("welcome to the dungeon %s",
        // playerName), "info");

        // // Game Loop
        // while (!game.getGameOver()) {
        // display.display();
        // game.gameLoop();
        // }

        // display.close();

        Component component = new Component();
        component.setTitle("wow");
        component.setBorder();

        component.writeBuffer("hmmmm");
        component.writeBuffer("asdfjaskdfjaslkdjf;laskjdfl;askjdf;laskdjfa;slkdfjasdlkf;j");
        component.writeBuffer("asdfjaskdfjaslkdjf;laskjdfl;askjdf;laskdjfa;slkdfjasdlkf;j");
        component.writeBuffer("asdfjaskdfjaslkdjf;laskjdfl;askjdf;laskdjfa;slkdfjasdlkf;j");
        component.writeBuffer("woahhh");

        for (String line : component.draw(30, 7)) {
            System.out.println(line);
        }
    }
}
