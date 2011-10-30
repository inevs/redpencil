package de.shg;

public class PriceCalculator {

	public class IllegalArgument extends RuntimeException {}
	public class TooShortDuration extends RuntimeException {}

	private Integer duration = 0;
	private Integer previousPrice;
	private int price = 0;

	public Integer getPrice() {
		return price;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void resetDuration() {
		this.duration = 0;
	}

	public void nextDay() {
		this.duration++;
		if (this.duration >= 30 && previousPrice != null)
			price = previousPrice;
	}

	public void reducePriceBy(int percentValue) {
		if (percentValue < 5 || percentValue > 30) throw new IllegalArgument();
		if (duration < 30) throw new TooShortDuration();

		previousPrice = price;
		price -= price * percentValue / 100;
		resetDuration();
	}
}
