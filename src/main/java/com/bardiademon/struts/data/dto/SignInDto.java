package com.bardiademon.struts.data.dto;


import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

public class SignInDto extends ActionForm {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        final ActionErrors actionErrors = new ActionErrors();

        if (username == null || username.isEmpty() || username.length() > 50) {
            actionErrors.add("username", new ActionMessage("error.username.required"));
        }
        if (password == null || password.isEmpty() || password.length() > 50) {
            actionErrors.add("password", new ActionMessage("error.password.required"));
        }

        return actionErrors;
    }
}
