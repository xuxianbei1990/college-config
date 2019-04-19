package college.Filter;

import college.wrapper.RepeatReadServletRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Name  过滤器
 *
 * @author xuxb
 * Date 2018-12-10
 * VersionV1.0
 * @description  https://www.cnblogs.com/chenhonggao/p/9027591.html
 *
 *  过滤器原理
 */
//@Component
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(new RepeatReadServletRequestWrapper((HttpServletRequest) servletRequest), servletResponse);
    }

    @Override
    public void destroy() {

    }
}
