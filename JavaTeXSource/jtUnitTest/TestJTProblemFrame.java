package jtUnitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javatex.example.MultiplicationExample;

@SuppressWarnings("static-method")
class TestJTProblemFrame {

	@Test
	void testMultiplicationExample() {
		MultiplicationExample me = new MultiplicationExample(1, 2);

		System.out.println(me.convert());

		assertEquals("", "");

	}

}
