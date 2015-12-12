package eu.hcomb.test;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.hcomb.authn.dto.TokenDTO;
import eu.hcomb.common.redis.TypedSubscriber;

public class TokenSubscriber extends TypedSubscriber<TokenDTO>{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public TokenSubscriber() {
		super(TokenDTO.class);
	}
	
	@Override
	public void onMessage(String channel, TokenDTO object) {
		System.out.println("received from channel "+ channel + " token: " + ToStringBuilder.reflectionToString(object, ToStringStyle.MULTI_LINE_STYLE));
	}

	
}
