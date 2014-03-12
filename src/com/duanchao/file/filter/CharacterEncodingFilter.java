package com.duanchao.file.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class CharacterEncodingFilter implements Filter {
	@SuppressWarnings("unused")
	private FilterConfig fc;

	public void destroy() {
		this.fc = null;
	}

	/**
	 * ±àÂë×ª»»µÄ¹ýÂËÆ÷
	 */
	public void doFilter(ServletRequest sRequest, ServletResponse sResponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) sRequest;
		request.setCharacterEncoding("gb2312");
		chain.doFilter(request, sResponse);
	}

	public void init(FilterConfig fc) throws ServletException {
		this.fc = fc;
	}
}