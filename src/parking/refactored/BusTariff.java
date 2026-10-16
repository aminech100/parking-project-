package parking.refactored;

public class BusTariff implements Tariff {
    @Override
    public double calculateFee(int hours){
        return hours * 15.0;
    }
}