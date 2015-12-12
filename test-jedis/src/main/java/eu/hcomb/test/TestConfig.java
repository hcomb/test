package eu.hcomb.test;

import eu.hcomb.common.redis.JedisConfig;
import eu.hcomb.common.redis.JedisConfigurable;
import eu.hcomb.common.web.BaseConfig;

public class TestConfig extends BaseConfig implements JedisConfigurable {

	protected JedisConfig jedisConfig = new JedisConfig();

	public JedisConfig getJedisConfig() {
		return jedisConfig;
	}

}
