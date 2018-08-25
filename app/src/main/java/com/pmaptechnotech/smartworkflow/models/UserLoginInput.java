package com.pmaptechnotech.smartworkflow.models;

/**
 * Created by intel on 12-02-18.
 */

public class UserLoginInput {
    public String user_name;
    public String user_password;

    // ALT INSERT FOR Constructor//

    public UserLoginInput(String user_name, String user_password) {
        this.user_name = user_name;
        this.user_password = user_password;
    }
}

