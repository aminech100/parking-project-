package parking.refactored;

public class MotorbikeTariff implements Tariff {
    public double calculateFee(int hours) { return hours * 2.0; }
}
