public class CharacterComponent {

    Component characterComponent;
    Component imageComponent;
    Component statsComponent;
    Character character;

    public CharacterComponent(Character character, boolean hasBorder) {
        this.character = character;

        this.imageComponent = new Component();
        this.imageComponent.setBorder();
        this.imageComponent.setTitle(character.getName());

        this.statsComponent = new Component();
        this.statsComponent.setMinWidth(20);
        this.statsComponent.setFixedWidth();
        this.statsComponent.setFixedHeight();

        this.characterComponent = new Component();
        if (hasBorder) this.characterComponent.setBorder();
        this.characterComponent.setFixedWidth();
        this.characterComponent.setColumnComponent();
        this.characterComponent.addChild(this.imageComponent);
        this.characterComponent.addChild(this.statsComponent);
    }

    public Component getComponent() {
        // Create a health bar
        int fill =
            16 * (this.character.currentHealth / this.character.maxHealth);

        this.statsComponent.writeBuffer(
                " [" + "■".repeat(fill) + "□".repeat(16 - fill) + "] "
            );
        this.statsComponent.writeBuffer("Damage: " + this.character.damage);
        this.statsComponent.writeBuffer("Defence: " + this.character.defence);

        return this.characterComponent;
    }
}
