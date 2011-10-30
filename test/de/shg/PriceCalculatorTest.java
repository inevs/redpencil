package de.shg;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PriceCalculatorTest {

	private PriceCalculator priceCalculator;

	@Before
	public void setUp() throws Exception {
		priceCalculator = new PriceCalculator();
		setDuration(30);
	}

	public void setDuration(int duration) {
		priceCalculator.resetDuration();
		for (int i=0; i<duration; i++) {
			priceCalculator.nextDay();
		}
	}

	@Test
	public void calulatorGivesPrice() throws Exception {
		assertThat(priceCalculator.getPrice(), CoreMatchers.<Object>notNullValue());
	}

	@Test
	public void calculatorSavesPrice() throws Exception {
		priceCalculator.setPrice(100);
		assertThat(priceCalculator.getPrice(), is(100));
	}

	@Test
	public void calculatorReducesPrice() throws Exception {
		priceCalculator.setPrice(100);
		priceCalculator.reducePriceBy(5);
		assertThat(priceCalculator.getPrice(), is(95));
	}

	@Test (expected = PriceCalculator.IllegalArgument.class)
	public void avoidsReductionByLessThan5Percent() throws Exception {
		priceCalculator.reducePriceBy(4);
	}

	@Test (expected = PriceCalculator.IllegalArgument.class)
	public void avoidsReductionByMoreThan30Percent() throws Exception {
		priceCalculator.reducePriceBy(31);
	}

	@Test
	public void calculatorGivesDuration() throws Exception {
		assertThat(priceCalculator.getDuration(), CoreMatchers.<Object>notNullValue());
	}

	@Test
	public void calculatorSavesDuration() throws Exception {
		setDuration(41);
		assertThat(priceCalculator.getDuration(), is(41));
	}

	@Test (expected = PriceCalculator.TooShortDuration.class)
	public void avoidsReductionOnDurationLessThan30() throws Exception {
		setDuration(29);
		priceCalculator.reducePriceBy(20);
	}

	@Test
	public void resetsDurationAfterReduction() throws Exception {
		priceCalculator.reducePriceBy(15);
		assertThat(priceCalculator.getDuration(), is(0));
	}

	@Test
	public void resetsPreviousPriceAfter30Days() throws Exception {
		priceCalculator.setPrice(100);
		priceCalculator.reducePriceBy(5);
		setDuration(30);
		assertThat(priceCalculator.getPrice(), is(100));
	}
}
