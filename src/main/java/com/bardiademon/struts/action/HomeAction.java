package com.bardiademon.struts.action;

import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.struts.service.UserService;
import com.bardiademon.struts.util.ConnectToDatabase;
import com.bardiademon.struts.util.Jwt;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.Arrays;

public class HomeAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            final String token = getToken(request.getCookies());
            if (token != null) {
                final Long userId = Jwt.getUserId(token);
                if (userId != null) {
                    try (final Connection connection = ConnectToDatabase.connectToDatabase()) {
                        final JjsonObject user = UserService.fetchUserById(connection, userId);
                        if (user != null) {
                            request.setAttribute("userName", user.getString("name"));
                            return mapping.findForward("home");
                        }
                    }
                }
            }
        } catch (Exception e) {
            return mapping.findForward("fail");
        }
        return mapping.findForward("signin");
    }


    public static String getToken(final Cookie[] cookies) {
        return Arrays.stream(cookies).filter(item -> item.getName().equals("token")).findFirst().map(Cookie::getValue).orElse(null);
    }
}
