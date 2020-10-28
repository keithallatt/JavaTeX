package jtUnitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javatex.JTProblemFrame;
import javatex.MultiplicationExample;

class TestJTProblemFrame {

	@Test
	void testMultiplicationExample() {
		MultiplicationExample me = new MultiplicationExample(1, 2);
		
		System.out.println(me.convert());
		
		assertEquals("", "");
	
	}

}
