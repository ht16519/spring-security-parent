package com.xh.security.browser.session.strategy;
import java.io.IOException;
import javax.servlet.ServletException;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * 默认并发登录时session失效处理策略
 */
public class DefaultConcurrentLoginSessionInvalidStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {

	public DefaultConcurrentLoginSessionInvalidStrategy(String invalidSessionUrl) {
		super(invalidSessionUrl);
	}

	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		onSessionInvalid(event.getRequest(), event.getResponse());
	}
	
	@Override
	protected boolean isConcurrency() {
		return true;
	}

}
