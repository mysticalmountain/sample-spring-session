package com.sample.spring.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by andongxu on 16-3-29.
 */
public class SessionFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filter init ...............");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("filter do ...............");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        System.out.println(req.getContextPath() + "\t" + req.getServletPath() + "\t" + req.getRequestURI() + "\t" );
        String [] ps = new String[] {"/", "/login"};

        String username = (String) req.getSession().getAttribute("username");
        boolean flag = false;
        if (username == null || username.length() == 0) {
            for(String p : ps) {
                if (p.equals(req.getRequestURI())) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                filterChain.doFilter(servletRequest, servletResponse);
            }else {
                HttpServletResponse rps = (HttpServletResponse) servletResponse;
                rps.sendRedirect("/");
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    public void destroy() {

    }
}
