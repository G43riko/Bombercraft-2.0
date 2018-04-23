package Bombercraft2.Bombercraft2.game.player.placers;

public enum PlacerType {
    SIMPLE("Simple"),
    AREA("Area");

    private final String label;

    PlacerType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
