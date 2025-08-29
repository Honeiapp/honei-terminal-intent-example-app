package app.honei.honei_terminal_intent_test_app.data;

import app.honei.honei_terminal_intent_test_app.models.MenuItemDef;
import app.honei.honei_terminal_intent_test_app.models.ModifierDef;

public class MenuData {
    public static final ModifierDef EXTRA_CHEESE = new ModifierDef("Extra cheese", 2.00);
    public static final ModifierDef BACON = new ModifierDef("Bacon", 1.50);
    public static final ModifierDef NO_ICE = new ModifierDef("No ice", 0.00);
    public static final ModifierDef SPECIAL_SAUCE = new ModifierDef("Special sauce", 0.80);
    public static final ModifierDef OAT_MILK = new ModifierDef("Oat milk", 0.40);

    public static final MenuItemDef[] MENU = new MenuItemDef[] {
            new MenuItemDef("Cheeseburger", 12.50, EXTRA_CHEESE, BACON, SPECIAL_SAUCE),
            new MenuItemDef("Margherita Pizza", 9.90, EXTRA_CHEESE, SPECIAL_SAUCE),
            new MenuItemDef("Caesar Salad", 8.50, BACON, SPECIAL_SAUCE),
            new MenuItemDef("Coca Cola", 3.50, NO_ICE),
            new MenuItemDef("Water", 2.00),
            new MenuItemDef("Coffee", 1.80, OAT_MILK),
            new MenuItemDef("Cheesecake", 4.90, SPECIAL_SAUCE)
    };
} 