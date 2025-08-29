package app.honei.honei_terminal_intent_test_app.ui;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class TableCard {
    private final LinearLayout card;
    private final Button chargeButton;
    private final Button printButton;
    private final TextView amountView;
    private final NumberFormat currencyFormatter;

    public TableCard(Activity activity, int tableIndex, 
                    OnChargeClickListener chargeListener,
                    OnPrintClickListener printListener) {
        
        currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("es", "ES"));
        
        card = new LinearLayout(activity);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(24, 24, 24, 24);
        card.setBackgroundColor(0xFFEFEFEF);

        TextView nameView = new TextView(activity);
        nameView.setText("Table " + (tableIndex + 1));
        nameView.setTextSize(16f);
        card.addView(nameView);

        amountView = new TextView(activity);
        amountView.setTextSize(18f);
        amountView.setPadding(0, 12, 0, 12);
        card.addView(amountView);

        chargeButton = new Button(activity);
        chargeButton.setText("Charge");
        chargeButton.setTextSize(12f);
        chargeButton.setOnClickListener(v -> chargeListener.onChargeClick(tableIndex));
        card.addView(chargeButton);

        printButton = new Button(activity);
        printButton.setText("Print Bill");
        printButton.setTextSize(12f);
        printButton.setOnClickListener(v -> printListener.onPrintClick(tableIndex));
        card.addView(printButton);
    }

    public LinearLayout getCard() {
        return card;
    }

    public void setAmount(double amount) {
        amountView.setText(currencyFormatter.format(amount));
    }

    public void setPaid(boolean paid) {
        if (paid) {
            card.setBackgroundColor(0xFFCCFFCC);
            chargeButton.setVisibility(android.view.View.GONE);
        } else {
            card.setBackgroundColor(0xFFEFEFEF);
            chargeButton.setVisibility(android.view.View.VISIBLE);
        }
    }

    public interface OnChargeClickListener {
        void onChargeClick(int tableIndex);
    }

    public interface OnPrintClickListener {
        void onPrintClick(int tableIndex);
    }
} 