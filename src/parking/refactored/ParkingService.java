package parking.refactored;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParkingService {
    private final Map<String, Tariff> tariffs;   //instead of using the case statements, I decided to use a Map which is the same concept of dictionaries already covered in python course
    private final Logger logger;
    private final ReceiptFormatter formatter;
    private final List<String> receipts = new ArrayList<>();
    private double total;

    public ParkingService(Map<String, Tariff> tariffs, Logger logger, ReceiptFormatter formatter) {
        this.tariffs = tariffs;
        this.logger = logger;
        this.formatter = formatter;
    }

    public double fee(Vehicle vehicle, int hours) {
        Tariff tariff = tariffs.get(vehicle.getType());
        if (tariff == null) {
            throw new IllegalArgumentException("Unknown vehicle type: " + vehicle.getType());
        }
        return tariff.calculateFee(hours);
    }

    public void checkout(Vehicle vehicle, int hours, PaymentMethod method) throws IOException {
        double amount = fee(vehicle, hours);
        method.pay(amount);
        total += amount;

        String receipt = formatter.formatReceipt(vehicle, hours, amount, method);
        receipts.add(receipt);
        logger.log(receipt);
    }

    public void cancel(Vehicle vehicle, int hours, RefundablePaymentMethod method) {
        double amount = fee(vehicle, hours);
        method.refund(amount);
        total -= amount;
    }

    public String dailyReport() {
        return formatter.formatReport(receipts, total);
    }
}
