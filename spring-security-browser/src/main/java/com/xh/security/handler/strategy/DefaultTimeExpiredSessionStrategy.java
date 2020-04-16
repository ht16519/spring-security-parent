/**
 * 
 */
package com.xh.security.handler.strategy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xh.security.session.strategy.AbstractSessionStrategy;
import org.springframework.security.web.session.InvalidSessionStrategy;

/**
 * 默认session超时失效处理策略
 */
public class DefaultTimeExpiredSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {

	public DefaultTimeExpiredSessionStrategy(String invalidSessionUrl) {
		super(invalidSessionUrl);
	}

	@Override
	public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		onSessionInvalid(request, response);
	}

}
