package se.wiklund.ld38.util;

public class UnitFormatter {
	
	public static String formatEnergy(int amount) {
		int temp = Math.abs(amount);
		if (temp > 1000000000) {
			return amount / 1000000000f + " GWh";
		}
		if (temp > 1000000) {
			return amount / 1000000f + " MWh";
		}
		if (temp > 1000) {
			return amount / 1000 + " kWh";
		}
		return amount + " Wh";
	}
}
