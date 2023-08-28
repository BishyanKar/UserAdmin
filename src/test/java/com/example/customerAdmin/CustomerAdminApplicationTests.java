package com.example.customerAdmin;

import com.example.customerAdmin.model.User;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
class CustomerAdminApplicationTests {

	@Test
	void contextLoads() {
		User user = new User("123", "Patrick", "Jane", "Delhi", "Delhi", "Delhi", "Delhi", "dwe", "132");
		Gson gson = new Gson();
		assertNotNull("", user.getUserJson());
	}

}
