package parking;

import java.io.IOException;

public class ParkingTest {
    public static void main(String[] args) throws IOException {
        ParkingGarage garage = new ParkingGarage();
        Vehicle truck = new Vehicle("24680-D-8", "TRUCK");
        PaymentMethod card = new CardPayment("4242");

        double fee = garage.fee(truck, 4);
        System.out.println("Calculated fee: " + fee); // Expected: 60.0

        garage.checkout(truck, 4, card);
    }
}
