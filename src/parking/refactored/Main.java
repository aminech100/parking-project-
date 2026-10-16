package parking.refactored;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        Map<String, Tariff> tariffs = new HashMap<>();
        tariffs.put("CAR", new CarTariff());
        tariffs.put("MOTORBIKE", new MotorbikeTariff());
        tariffs.put("TRUCK", new TruckTariff());
        tariffs.put("BUS", new BusTariff()); // <-this is for qst 10

        Logger logger = new FileLogger("parking.log");
        ReceiptFormatter formatter = new ReceiptFormatter();

        ParkingService garage = new ParkingService(tariffs, logger, formatter);

        Vehicle car   = new Vehicle("12345-A-6", "CAR");
        Vehicle bike  = new Vehicle("67890-B-1", "MOTORBIKE");
        Vehicle truck = new Vehicle("24680-D-8", "TRUCK");

        RefundablePaymentMethod card = new CardPayment("4242");
        PaymentMethod cash = new CashPayment();

        garage.checkout(car, 3, card);
        garage.checkout(bike, 2, cash);
        garage.checkout(truck, 4, card);

        double bikeFee = garage.fee(bike, 2);
        System.out.println("Refund " + bikeFee + " MAD at the desk for " + bike.getPlate());

        garage.cancel(truck, 4, card);

        System.out.println(garage.dailyReport());

    }
}
