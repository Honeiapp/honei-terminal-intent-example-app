package app.honei.honei_terminal_intent_test_app.models;

import java.util.ArrayList;
import java.util.List;

public class TicketItem {
    private int units;
    private String name;
    private double unitPrice;
    private double price;
    private List<ModifierDef> modifiers = new ArrayList<>();

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<ModifierDef> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<ModifierDef> modifiers) {
        this.modifiers = modifiers;
    }
} 