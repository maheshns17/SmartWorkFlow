package com.pmaptechnotech.smartworkflow.models;

/**
 * Created by intel on 17-04-18.
 */

public class ResetPasswordInput {
    public String user_mobile;
    public String user_new_password;

    public ResetPasswordInput(String user_mobile, String user_new_password) {
        this.user_mobile = user_mobile;
        this.user_new_password = user_new_password;
    }
}
