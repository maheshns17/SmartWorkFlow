package com.pmaptechnotech.smartworkflow.models;

import java.util.List;

import setsgets.RegisterUsers;
import setsgets.User;

/**
 * Created by intel on 10-02-18.
 */

public class UserRegisterResult {

    public boolean is_success;
    public String msg;
    public List<RegisterUsers> users;
}
