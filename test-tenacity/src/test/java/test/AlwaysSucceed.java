package test;

import com.yammer.tenacity.core.TenacityCommand;
import com.yammer.tenacity.core.properties.TenacityPropertyKey;

public class AlwaysSucceed extends TenacityCommand<String> {
    public AlwaysSucceed() {
        super(new TenacityPropertyKey() {
			public String name() {
				return "a";
			}
		});
    }

    @Override
    protected String run() throws Exception {
    	if(true)
    		throw new RuntimeException("a");
        return "value";
    }
    
    
}