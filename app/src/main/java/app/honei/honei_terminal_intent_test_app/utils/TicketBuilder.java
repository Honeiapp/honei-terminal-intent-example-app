package app.honei.honei_terminal_intent_test_app.utils;

import app.honei.honei_terminal_intent_test_app.models.ModifierDef;
import app.honei.honei_terminal_intent_test_app.models.TicketItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TicketBuilder {
    private static final double TAX_RATE = 0.10;
    
    public static String buildTicketPayload(int tableIndex, List<TicketItem> items) {
        String dateStr = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"))
                .format(new Date());

        String tableName = "Table " + (tableIndex + 1);
        String ticketNumber = String.valueOf(10 + tableIndex);

        double subtotal = 0.0;
        for (TicketItem ti : items) subtotal += ti.getPrice();
        subtotal = MathUtils.round2(subtotal);
        double taxAmount = MathUtils.round2(subtotal * TAX_RATE);
        double total = MathUtils.round2(subtotal + taxAmount);

        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("\"tableName\":\"").append(tableName).append("\",")
                .append("\"ticketNumber\":\"").append(ticketNumber).append("\",")
                .append("\"numberOfGuests\":").append(2).append(",")
                .append("\"date\":\"").append(dateStr).append("\",")
                .append("\"subtotal\":").append(MathUtils.format2(subtotal)).append(",")
                .append("\"total\":").append(MathUtils.format2(total)).append(",")
                .append("\"taxAmount\":").append(MathUtils.format2(taxAmount)).append(",")
                .append("\"taxRate\":").append(MathUtils.format2(TAX_RATE)).append(",")
                .append("\"items\":[");
        
        for (int i = 0; i < items.size(); i++) {
            TicketItem ti = items.get(i);
            sb.append("{")
                    .append("\"units\":").append(ti.getUnits()).append(",")
                    .append("\"name\":\"").append(ti.getName()).append("\",")
                    .append("\"unitPrice\":").append(MathUtils.format2(ti.getUnitPrice())).append(",")
                    .append("\"price\":").append(MathUtils.format2(ti.getPrice())).append(",")
                    .append("\"modifierOptions\":[");
            
            for (int j = 0; j < ti.getModifiers().size(); j++) {
                ModifierDef md = ti.getModifiers().get(j);
                sb.append("{\"name\":\"").append(md.getName()).append("\",")
                        .append("\"supl\":").append(MathUtils.format2(md.getSupplement())).append("}");
                if (j < ti.getModifiers().size() - 1) sb.append(",");
            }
            sb.append("]}");
            if (i < items.size() - 1) sb.append(",");
        }
        sb.append("]}");

        return sb.toString();
    }
} 