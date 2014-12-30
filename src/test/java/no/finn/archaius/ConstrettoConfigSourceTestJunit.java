package no.finn.archaius;

import com.netflix.config.*;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
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
//        System.out.println(res);

    }

    @Test
    public void testWithHystrix() {
        TestItCommand tc = new TestItCommand();
        String result = tc.execute();
        Integer timeout = tc.getProperties().executionIsolationThreadTimeoutInMilliseconds().get();
        assertEquals("get the timeout from the config file", 998, timeout.longValue());
        //System.out.println(timeout);
//        System.out.println("key: " + tc.getCommandKey().name());
    }

    static class TestItCommand extends HystrixCommand<String> {

        TestItCommand() {
            super(HystrixCommandGroupKey.Factory.asKey("TestItGroup"));
        }

        @Override
        protected String run() throws Exception {
            return null;
        }
    }

}