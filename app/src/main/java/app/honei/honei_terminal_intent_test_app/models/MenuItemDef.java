package app.honei.honei_terminal_intent_test_app.models;

public class MenuItemDef {
    private final String name;
    private final double unitPrice;
    private final ModifierDef[] possibleModifiers;

    public MenuItemDef(String name, double unitPrice, ModifierDef... modifiers) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.possibleModifiers = modifiers;
    }

    public String getName() {
        return name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public ModifierDef[] getPossibleModifiers() {
        return possibleModifiers;
    }
} 