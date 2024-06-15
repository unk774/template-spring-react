package com.example.template;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"spring.jpa.properties.hibernate.javax.cache.uri = classpath://hibernateCache.xml"})
class TemplateApplicationTests {

	@Test
	void contextLoads() {
	}

}
