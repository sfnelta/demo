package de.nelta.demo.api.base;

import de.nelta.demo.api.client.AuthClient;
import de.nelta.demo.api.client.OrderClient;
import de.nelta.demo.api.config.ConfigLoader;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
    protected static AuthClient authClient;
    protected static OrderClient orderClient;
    protected static String token;

    @BeforeSuite
    public void setupSuite() {
        String baseUrl = ConfigLoader.getProperty("base.url");
        int port = ConfigLoader.getIntProperty("base.port");
        
        authClient = new AuthClient(baseUrl, port);
        orderClient = new OrderClient(baseUrl, port);
        
        // Obtain token for secured tests
        token = authClient.getToken(
                ConfigLoader.getProperty("admin.username"),
                ConfigLoader.getProperty("admin.password")
        );
    }
}
