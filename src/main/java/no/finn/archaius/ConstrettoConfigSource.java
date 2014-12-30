package no.finn.archaius;

import com.netflix.config.PollResult;
import com.netflix.config.PolledConfigurationSource;
import org.constretto.ConstrettoConfiguration;
import org.constretto.Property;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Trivial adapter for using Constretto as a backing configuration source for Archaius
 *
 * Henning Spjelkavik, FINN.no 2014
 *
 */
public class ConstrettoConfigSource implements PolledConfigurationSource {

    private final ConstrettoConfiguration config;

    public ConstrettoConfigSource(ConstrettoConfiguration config) {
        this.config = config;
    }

    @Override
    public PollResult poll(boolean initial, Object checkPoint) throws Exception {
        Map<String, Object> m = new HashMap<String, Object>();
        for (Property p : config) {
            m.put(p.getKey(), p.getValue());
        }
        return PollResult.createFull(m);
    }

}
