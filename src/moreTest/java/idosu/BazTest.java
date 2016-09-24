package idosu;

import java.lang.Math;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BazTest {
	private Baz baz;

	@Before
	public void setUp() {
		baz = new Baz();
	}

	@Test
	public void foo() {
		String expected = Double.toString(Math.random());
		baz.foo(expected);

		assertEquals(expected, baz.foo());
	}
}
