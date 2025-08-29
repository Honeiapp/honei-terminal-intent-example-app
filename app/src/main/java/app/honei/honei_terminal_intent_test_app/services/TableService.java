package app.honei.honei_terminal_intent_test_app.services;

import app.honei.honei_terminal_intent_test_app.data.MenuData;
import app.honei.honei_terminal_intent_test_app.models.MenuItemDef;
import app.honei.honei_terminal_intent_test_app.models.ModifierDef;
import app.honei.honei_terminal_intent_test_app.models.TicketItem;
import app.honei.honei_terminal_intent_test_app.utils.MathUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TableService {
    private static final int TABLE_COUNT = 12;
    private static final int MIN_ITEMS_PER_TABLE = 1;
    private static final int MAX_ITEMS_PER_TABLE = 4;
    private static final int MIN_UNITS = 1;
    private static final int MAX_UNITS = 3;
    private static final double TAX_RATE = 0.10;

    private final double[] tableAmounts = new double[TABLE_COUNT];
    private final boolean[] tablePaid = new boolean[TABLE_COUNT];
    private final List<TicketItem>[] tableItems = new ArrayList[TABLE_COUNT];

    public TableService() {
        generateRandomTables();
    }

    public void generateRandomTables() {
        Random rnd = new Random();
        for (int i = 0; i < TABLE_COUNT; i++) {
            List<TicketItem> items = new ArrayList<>();
            int itemCount = rnd.nextInt(MAX_ITEMS_PER_TABLE - MIN_ITEMS_PER_TABLE + 1) + MIN_ITEMS_PER_TABLE;

            for (int k = 0; k < itemCount; k++) {
                MenuItemDef chosen = MenuData.MENU[rnd.nextInt(MenuData.MENU.length)];
                TicketItem ti = new TicketItem();
                ti.setUnits(rnd.nextInt(MAX_UNITS - MIN_UNITS + 1) + MIN_UNITS);
                ti.setName(chosen.getName());
                ti.setUnitPrice(MathUtils.round2(chosen.getUnitPrice()));

                if (chosen.getPossibleModifiers() != null && chosen.getPossibleModifiers().length > 0) {
                    int modsToAdd = rnd.nextInt(3);
                    for (int m = 0; m < modsToAdd; m++) {
                        ModifierDef mod = chosen.getPossibleModifiers()[rnd.nextInt(chosen.getPossibleModifiers().length)];
                        boolean exists = false;
                        for (ModifierDef md : ti.getModifiers()) {
                            if (md.getName().equals(mod.getName())) {
                                exists = true;
                                break;
                            }
                        }
                        if (!exists) ti.getModifiers().add(mod);
                    }
                }

                double modsSum = 0.0;
                for (ModifierDef md : ti.getModifiers()) modsSum += md.getSupplement();

                ti.setPrice(MathUtils.round2(ti.getUnits() * (ti.getUnitPrice() + modsSum)));
                items.add(ti);
            }

            double subtotal = 0.0;
            for (TicketItem ti : items) subtotal += ti.getPrice();
            subtotal = MathUtils.round2(subtotal);
            double taxAmount = MathUtils.round2(subtotal * TAX_RATE);
            double total = MathUtils.round2(subtotal + taxAmount);

            tableItems[i] = items;
            tablePaid[i] = false;
            tableAmounts[i] = total;
        }
    }

    public int getTableCount() {
        return TABLE_COUNT;
    }

    public double getTableAmount(int index) {
        return tableAmounts[index];
    }

    public boolean isTablePaid(int index) {
        return tablePaid[index];
    }

    public void setTablePaid(int index, boolean paid) {
        tablePaid[index] = paid;
    }

    public List<TicketItem> getTableItems(int index) {
        return tableItems[index];
    }
} 