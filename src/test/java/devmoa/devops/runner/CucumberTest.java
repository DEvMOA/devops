package devmoa.devops.runner;

import devmoa.devops.DevopsApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@CucumberContextConfiguration
@SpringBootTest(classes = DevopsApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class CucumberTest {
}
