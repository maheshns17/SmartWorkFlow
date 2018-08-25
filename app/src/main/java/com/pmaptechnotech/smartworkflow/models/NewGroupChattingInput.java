package com.pmaptechnotech.smartworkflow.models;

import java.util.List;

/**
 * Created by intel on 15-04-18.
 */

public class NewGroupChattingInput {

    public String group_name;
    public String group_created_user_id;
    public List<UserID> group_members_id;

    public NewGroupChattingInput(String group_name, String group_created_user_id, List<UserID> group_members_id) {
        this.group_name = group_name;
        this.group_created_user_id = group_created_user_id;
        this.group_members_id = group_members_id;
    }
}
