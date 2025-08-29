# Honei Terminal Intent Test App

A test Android application for integrating with Honei Terminal payment system. This app simulates a Point of Sale (POS) system with multiple tables and demonstrates how to integrate payment and printing functionality.

## Features

- **Table Management**: 12 tables with randomly generated orders
- **Payment Integration**: Test payment processing with Honei Terminal
- **Ticket Printing**: Generate and print receipts for tables
- **Clean Architecture**: Separated concerns with models, services, and UI components

## Project Structure

```
app/src/main/java/app/honei/honei_terminal_intent_test_app/
├── MainActivity.java                 # Main activity with UI setup
├── models/                          # Data models
│   ├── ModifierDef.java            # Menu item modifiers
│   ├── MenuItemDef.java            # Menu item definitions
│   └── TicketItem.java             # Ticket line items
├── data/
│   └── MenuData.java               # Menu configuration
├── services/
│   └── TableService.java           # Table management and data generation
├── utils/
│   ├── MathUtils.java              # Math utility functions
│   └── TicketBuilder.java          # JSON ticket generation
└── ui/
    └── TableCard.java              # Table card UI component
```

## Getting Started

### Prerequisites

- Android Studio Arctic Fox or later
- Android SDK API level 21 or higher
- Honei Terminal app installed on the device

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Honeiapp/honei-terminal-intent-test-app.git
   cd honei-terminal-intent-test-app
   ```

2. Open the project in Android Studio

3. Sync Gradle files and build the project

4. Install the app on your device

### Usage

1. **Launch the app**: The main screen shows 12 tables with randomly generated orders

2. **Charge a table**: 
   - Tap "Charge" on any table
   - Confirm the payment amount
   - The Honei Terminal app will open for payment processing
   - After successful payment, the table will be marked as paid (green background)

3. **Print a ticket**:
   - Tap "Print Bill" on any table
   - A receipt will be generated and sent to the printer

## Integration Details

### Payment Intent

The app sends payment intents to Honei Terminal:

```java
Intent intent = new Intent("app.honei.terminal.INIT_PAYMENT");
intent.putExtra("amount", "12.50");
startActivityForResult(intent, REQ_PAYMENT);
```

### Payment Response

The payment result includes:
- `status`: "completed" for successful payments
- `amount`: The charged amount
- `tip`: Tip amount (if any)
- `payment_id`: Unique payment identifier

### Print Intent

The app sends print intents with JSON ticket data:

```java
Intent intent = new Intent("app.honei.terminal.PRINT_TICKET");
intent.putExtra("data", jsonTicketPayload);
startActivityForResult(intent, REQ_PRINT);
```

## Configuration

### Menu Items

Edit `MenuData.java` to customize the menu items and modifiers:

```java
public static final MenuItemDef[] MENU = new MenuItemDef[] {
    new MenuItemDef("Cheeseburger", 12.50, EXTRA_CHEESE, BACON, SPECIAL_SAUCE),
    // Add more items...
};
```

### Table Count

Modify `TABLE_COUNT` in `TableService.java` to change the number of tables.

## Development

### Building

```bash
./gradlew assembleDebug
```

### Testing

```bash
./gradlew test
```

## License

This project is proprietary software owned by Honei.

## Support

For support and questions, please contact the Honei development team. 