package app.honei.honei_terminal_intent_test_app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import app.honei.honei_terminal_intent_test_app.services.TableService;
import app.honei.honei_terminal_intent_test_app.ui.TableCard;
import app.honei.honei_terminal_intent_test_app.utils.TicketBuilder;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends Activity implements TableCard.OnChargeClickListener, TableCard.OnPrintClickListener {

    private static final String TAG = "TestCallerPOS";
    private static final int REQ_PAYMENT = 1001;
    private static final int REQ_PRINT = 2001;
    private static final int GRID_COLUMNS = 3;

    private TableService tableService;
    private GridLayout grid;
    private NumberFormat currencyFormatter;
    private int pendingTableIndex = -1;
    private TableCard[] tableCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tableService = new TableService();
        currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("es", "ES"));
        tableCards = new TableCard[tableService.getTableCount()];

        setupUI();
    }

    private void setupUI() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(36, 36, 36, 36);
        root.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        TextView title = new TextView(this);
        title.setText("Test POS");
        title.setTextSize(20f);
        root.addView(title);

        ScrollView scroll = new ScrollView(this);
        root.addView(scroll, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f));

        grid = new GridLayout(this);
        grid.setColumnCount(GRID_COLUMNS);
        grid.setUseDefaultMargins(true);
        scroll.addView(grid);

        buildGrid();
        setContentView(root);
    }

    private void buildGrid() {
        grid.removeAllViews();
        for (int i = 0; i < tableService.getTableCount(); i++) {
            TableCard tableCard = new TableCard(this, i, this, this);
            tableCard.setAmount(tableService.getTableAmount(i));
            tableCard.setPaid(tableService.isTablePaid(i));
            
            GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
            lp.width = 0;
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            lp.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            lp.setMargins(12, 12, 12, 12);
            tableCard.getCard().setLayoutParams(lp);

            tableCards[i] = tableCard;
            grid.addView(tableCard.getCard());
        }
    }

    @Override
    public void onChargeClick(int tableIndex) {
        double amount = tableService.getTableAmount(tableIndex);
        new AlertDialog.Builder(this)
                .setTitle("Charge Table " + (tableIndex + 1))
                .setMessage("Do you want to charge " + currencyFormatter.format(amount) + "?")
                .setPositiveButton("Charge", (d, which) -> startPaymentForTable(tableIndex, amount))
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onPrintClick(int tableIndex) {
        String payload = TicketBuilder.buildTicketPayload(tableIndex, tableService.getTableItems(tableIndex));

        Intent intent = new Intent("app.honei.terminal.PRINT_TICKET");
        intent.putExtra("data", payload);
        startActivityForResult(intent, REQ_PRINT);
    }

    private void startPaymentForTable(int tableIndex, double amount) {
        pendingTableIndex = tableIndex;

        Intent intent = new Intent("app.honei.terminal.INIT_PAYMENT");
        intent.putExtra("amount", String.format(Locale.US, "%.2f", amount));

        startActivityForResult(intent, REQ_PAYMENT);
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_PAYMENT) {
            handlePaymentResult(resultCode, data);
        } else if (requestCode == REQ_PRINT) {
            handlePrintResult(resultCode, data);
        }
    }

    private void handlePaymentResult(int resultCode, Intent data) {
        if (data == null) {
            showResultDialog(false, 0.0, 0.0, "No data");
            return;
        }
        
        String status = data.getStringExtra("status");
        double amount = data.getDoubleExtra("amount", 0.0);
        double tip = data.getDoubleExtra("tip", 0.0);
        String paymentId = data.getStringExtra("payment_id");

        boolean ok = (resultCode == RESULT_OK) && "completed".equalsIgnoreCase(status);

        String log = "status=" + status + ", amount=" + amount + ", tip=" + tip + ", paymentId=" + paymentId;
        Log.d(TAG, "Payment result: " + log);

        if (ok && pendingTableIndex >= 0 && pendingTableIndex < tableService.getTableCount()) {
            tableService.setTablePaid(pendingTableIndex, true);
            tableCards[pendingTableIndex].setPaid(true);
        }

        showResultDialog(ok, amount, tip, null);
        pendingTableIndex = -1;
    }

    private void handlePrintResult(int resultCode, Intent data) {
        boolean ok = (resultCode == RESULT_OK);
        String status = data != null ? data.getStringExtra("status") : null;
        String msg = ok ? "Ticket sent to printer" : "Could not print ticket";
        if (status != null) msg += "\nStatus: " + status;

        new AlertDialog.Builder(this)
                .setTitle(ok ? "🖨️ Print" : "🖨️ Print Error")
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .show();
    }

    private void showResultDialog(boolean ok, double amount, double tip, String fallbackMsg) {
        String title = ok ? "✅ Payment completed" : "❌ Payment failed";
        StringBuilder msg = new StringBuilder();

        if (fallbackMsg != null) {
            msg.append(fallbackMsg);
        } else {
            msg.append("Amount: ").append(currencyFormatter.format(amount));
            if (tip > 0.0) {
                msg.append("\nTip: ").append(currencyFormatter.format(tip));
            }
        }

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg.toString())
                .setPositiveButton("OK", null)
                .show();
    }
}
