package no.finn.archaius;

import com.netflix.config.*;
import org.constretto.ConstrettoConfiguration;
import org.junit.Test;

import static org.constretto.util.StaticlyCachedConfiguration.config;
import static org.junit.Assert.*;

public class ConstrettoConfigSourceTestJunit {

    @Test
    public void testConfig() {
        ConstrettoConfiguration CONFIGURATION = config("classpath:test.ini", "classpath:test-override.ini");
        ConstrettoConfigSource configSource = new ConstrettoConfigSource(CONFIGURATION);

        DynamicConfiguration dynConfig = new DynamicConfiguration(configSource, new FixedDelayPollingScheduler());

        ConfigurationManager.install(dynConfig);

        DynamicStringProperty myprop = DynamicPropertyFactory.getInstance().getStringProperty("alfa1", "1000");
        String res = myprop.get();
        assertEquals("42", res);
        System.out.println(res);

    }

}