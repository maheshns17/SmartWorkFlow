package com.pmaptechnotech.smartworkflow.models;

/**
 * Created by intel on 14-04-18.
 */

public class UserChatInput {
    public String chat_from_id;
    public String chat_to_id;
    public String chat_msg;
    public String chat_group_id;

    public UserChatInput(String chat_from_id, String chat_to_id, String chat_msg, String chat_group_id) {
        this.chat_from_id = chat_from_id;
        this.chat_to_id = chat_to_id;
        this.chat_msg = chat_msg;
        this.chat_group_id = chat_group_id;
    }
}
