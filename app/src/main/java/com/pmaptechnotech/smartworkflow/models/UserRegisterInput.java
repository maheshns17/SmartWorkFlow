package com.pmaptechnotech.smartworkflow.models;

/**
 * Created by intel on 10-02-18.
 */

public class UserRegisterInput {

    public String user_name;
    public String user_email;
    public String user_mobile;
    public String user_password;

// ALT INSERT FOR Constructor//

    public UserRegisterInput(String user_name, String user_email, String user_mobile, String user_password) {
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_mobile = user_mobile;
        this.user_password = user_password;
    }
}


