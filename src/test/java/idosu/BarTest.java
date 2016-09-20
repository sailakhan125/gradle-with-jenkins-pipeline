package idosu;

import java.lang.Math;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BarTest {
	private Bar bar;

	@Before
	public void setUp() {
		bar = new Bar();
	}

	@Test
	public void foo() {
		String expected = Double.toString(Math.random());
		bar.foo(expected);

		assertEquals(expected, bar.foo());
	}
}
