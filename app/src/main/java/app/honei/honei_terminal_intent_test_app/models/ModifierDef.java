package app.honei.honei_terminal_intent_test_app.models;

public class ModifierDef {
    private final String name;
    private final double supplement;

    public ModifierDef(String name, double supplement) {
        this.name = name;
        this.supplement = supplement;
    }

    public String getName() {
        return name;
    }

    public double getSupplement() {
        return supplement;
    }
} 