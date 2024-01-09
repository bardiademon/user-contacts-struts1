package com.bardiademon.struts.action;

import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.struts.data.dto.SignInDto;
import com.bardiademon.struts.service.UserService;
import com.bardiademon.struts.util.ConnectToDatabase;
import com.bardiademon.struts.util.Jwt;
import org.apache.struts.action.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

public class SignInAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final SignInDto user = (SignInDto) form;

        final ActionErrors errors = user.validate(mapping, request);
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return mapping.findForward("fail");
        }

        try (final Connection connection = ConnectToDatabase.connectToDatabase()) {
            final JjsonObject userFetch = UserService.fetchUserByUsernameAndPassword(connection, user.getUsername(), user.getPassword());
            if (userFetch != null && !userFetch.isEmpty()) {
                final String token = Jwt.createToken(userFetch.getLong("id"));
                if (token != null) {
                    request.setAttribute("userName", userFetch.getString("name"));
                    response.addCookie(new Cookie("token", token));
                    return mapping.findForward("success");
                }
            }
        }
        return mapping.findForward("fail");
    }
}
