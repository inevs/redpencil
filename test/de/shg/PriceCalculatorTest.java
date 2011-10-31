package de.shg;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PriceCalculatorTest {

	private PriceCalculator priceCalculator;

	@Before
	public void setUp() throws Exception {
		priceCalculator = new PriceCalculator();
	}

	public void setDuration(int duration) {
		priceCalculator.resetDuration();
		for (int i=0; i<duration; i++) {
			priceCalculator.nextDay();
		}
	}

	private void createInitialPrice(int price) {
		priceCalculator.setPrice(price);
		setDuration(30);
	}

	@Test
	public void calulatorGivesPrice() {
		assertThat(priceCalculator.getPrice(), CoreMatchers.<Object>notNullValue());
	}

	@Test
	public void calculatorSavesPrice() {
		priceCalculator.setPrice(100);
		assertThat(priceCalculator.getPrice(), is(100));
	}

	@Test
	public void calculatorReducesPrice() {
		createInitialPrice(100);
		priceCalculator.reducePriceBy(5);
		assertThat(priceCalculator.getPrice(), is(95));
	}

	@Test (expected = PriceCalculator.IllegalArgument.class)
	public void avoidsReductionByLessThan5Percent() {
		priceCalculator.reducePriceBy(4);
	}

	@Test (expected = PriceCalculator.IllegalArgument.class)
	public void avoidsReductionByMoreThan30Percent() {
		priceCalculator.reducePriceBy(31);
	}

	@Test
	public void calculatorGivesDuration() {
		assertThat(priceCalculator.getDuration(), CoreMatchers.<Object>notNullValue());
	}

	@Test
	public void calculatorSavesDuration() {
		setDuration(41);
		assertThat(priceCalculator.getDuration(), is(41));
	}

	@Test (expected = PriceCalculator.TooShortDuration.class)
	public void avoidsReductionOnDurationLessThan30() {
		setDuration(29);
		priceCalculator.reducePriceBy(20);
	}

	@Test
	public void resetsDurationAfterReduction() {
		setDuration(30);
		priceCalculator.reducePriceBy(15);
		assertThat(priceCalculator.getDuration(), is(0));
	}

	@Test
	public void resetsPreviousPriceAfter30Days() {
		createInitialPrice(100);
		priceCalculator.reducePriceBy(10);
		setDuration(30);
		assertThat(priceCalculator.getPrice(), is(100));
	}

	@Test
	public void priceIncreaseEndsPromotion() {
		createInitialPrice(100);
		priceCalculator.reducePriceBy(10);
		setDuration(15);
		priceCalculator.setPrice(95);
		assertThat(priceCalculator.getPrice(), is(95));
		assertThat(priceCalculator.getDuration(), is(0));
	}

	@Test
	@Ignore ("Start here")
	public void furtherPriceReductionWillNotProlongPromotion() {
		createInitialPrice(100);
		priceCalculator.reducePriceBy(10);
		setDuration(15);
		priceCalculator.reducePriceBy(10);
		assertThat(priceCalculator.getDuration(), is(15));
		assertThat(priceCalculator.getPrice(), is(81));
	}
}
