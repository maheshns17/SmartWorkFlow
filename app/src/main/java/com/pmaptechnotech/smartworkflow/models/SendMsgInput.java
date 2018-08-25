package com.pmaptechnotech.smartworkflow.models;

public class SendMsgInput {
    public String chat_from_id;
    public String chat_to_id;
    public String chat_msg;
    public String chat_group_id;

    public SendMsgInput(String chat_from_id, String chat_to_id, String chat_msg, String chat_group_id) {
        this.chat_from_id = chat_from_id;
        this.chat_to_id = chat_to_id;
        this.chat_msg = chat_msg;
        this.chat_group_id = chat_group_id;
    }
}
