package com.sample.spring.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by andongxu on 16-3-28.
 */
@Controller
public class LoginController {

    @RequestMapping(value = "login", method= RequestMethod.POST)
    public String login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null || username.trim().length() == 0 || password == null || password.trim().length() == 0) {
            return "login";
        }
        request.getSession().setAttribute("username", username);
        return "main";
    }

    @RequestMapping(value = "logout", method= RequestMethod.GET)
    public String logout (HttpServletRequest request) {
        request.getSession().invalidate();
        return "login";
    }
}
