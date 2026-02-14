package com.lab.SoporteApp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SoporteAppApplicationTests {

	@Test
	void testControllerResponse() {
		testController controller = new testController();
		String response = controller.test();

		assertEquals("Hello World!", response);
	}
}
