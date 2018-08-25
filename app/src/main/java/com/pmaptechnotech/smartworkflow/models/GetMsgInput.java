package com.pmaptechnotech.smartworkflow.models;

public class GetMsgInput {
    public String chat_from_id ;
    public String chat_to_id ;
    public String chat_group_id ;


    public GetMsgInput(String chat_from_id, String chat_to_id, String chat_group_id) {
        this.chat_from_id = chat_from_id;
        this.chat_to_id = chat_to_id;
        this.chat_group_id = chat_group_id;
    }
}
