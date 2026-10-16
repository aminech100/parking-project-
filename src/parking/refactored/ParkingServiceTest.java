package parking.refactored;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingServiceTest {
    // We will create a Fake Logger class that stores logs in a list instead of a file
    static class FakeLogger implements Logger{
        private final List<String> logs = new ArrayList<>();

        @Override
        public void log(String line){
            logs.add(line);
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Running ParkingService tests...");

        Map<String, Tariff> tariffs = new HashMap<>();
        tariffs.put("CAR", new CarTariff());
        tariffs.put("MOTORBIKE", new MotorbikeTariff());
        tariffs.put("TRUCK", new TruckTariff());

        FakeLogger fakeLogger = new FakeLogger();
        ReceiptFormatter formatter = new ReceiptFormatter();
        ParkingService garage = new ParkingService(tariffs, fakeLogger, formatter);

        Vehicle car   = new Vehicle("12345-A-6", "CAR");
        Vehicle bike  = new Vehicle("67890-B-1", "MOTORBIKE");
        Vehicle truck = new Vehicle("24680-D-8", "TRUCK");

        RefundablePaymentMethod card = new CardPayment("4242");
        PaymentMethod cash = new CashPayment();

        assert garage.fee(car, 3) == 15.0 : "Car fee calculation failed!";
        assert garage.fee(bike, 2) == 4.0 : "Motorbike fee calculation failed!";
        assert garage.fee(truck, 4) == 60.0 : "Truck fee calculation failed!";
        System.out.println("-> All fee calculations passed successfully!");


        garage.checkout(car, 3, card);
        garage.checkout(bike, 2, cash);
        garage.checkout(truck, 4, card);

        String report = garage.dailyReport();
        System.out.println("\nGenerated Daily Report:\n" + report);

        System.out.println("\n-> All tests completed successfully without creating any files!");
    }
}


