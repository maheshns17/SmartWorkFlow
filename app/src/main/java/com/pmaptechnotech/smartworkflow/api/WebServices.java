package com.pmaptechnotech.smartworkflow.api;

import com.pmaptechnotech.smartworkflow.models.AssignGrpInput;
import com.pmaptechnotech.smartworkflow.models.AssignGrpResult;
import com.pmaptechnotech.smartworkflow.models.AssignIndiInput;
import com.pmaptechnotech.smartworkflow.models.AssignIndiResult;
import com.pmaptechnotech.smartworkflow.models.GetGroupsInput;
import com.pmaptechnotech.smartworkflow.models.GetGroupsResult;
import com.pmaptechnotech.smartworkflow.models.GetMsgInput;
import com.pmaptechnotech.smartworkflow.models.GetMsgResult;
import com.pmaptechnotech.smartworkflow.models.GetMyTaskGRPInput;
import com.pmaptechnotech.smartworkflow.models.GetMyTaskGRPResult;
import com.pmaptechnotech.smartworkflow.models.GetMyTaskINDIInput;
import com.pmaptechnotech.smartworkflow.models.GetMyTaskINDIResult;
import com.pmaptechnotech.smartworkflow.models.GetTaskToMeGRPInput;
import com.pmaptechnotech.smartworkflow.models.GetTaskToMeGRPResult;
import com.pmaptechnotech.smartworkflow.models.GetTaskToMeINDIInput;
import com.pmaptechnotech.smartworkflow.models.GetTaskToMeINDIResult;
import com.pmaptechnotech.smartworkflow.models.GetUsersResult;
import com.pmaptechnotech.smartworkflow.models.NewGroupChattingInput;
import com.pmaptechnotech.smartworkflow.models.NewGroupChattingResult;
import com.pmaptechnotech.smartworkflow.models.ResetPasswordInput;
import com.pmaptechnotech.smartworkflow.models.ResetPasswordResult;
import com.pmaptechnotech.smartworkflow.models.SendMsgInput;
import com.pmaptechnotech.smartworkflow.models.SendMsgResult;
import com.pmaptechnotech.smartworkflow.models.UserChatInput;
import com.pmaptechnotech.smartworkflow.models.UserChatResult;
import com.pmaptechnotech.smartworkflow.models.UserLoginInput;
import com.pmaptechnotech.smartworkflow.models.UserLoginResult;
import com.pmaptechnotech.smartworkflow.models.UserRegisterInput;
import com.pmaptechnotech.smartworkflow.models.UserRegisterResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface WebServices {

    @POST("UserLogin_c/userLogin")
    Call<UserLoginResult> userLogin(@Body UserLoginInput input);

    @POST("UserRegister_c/userRegister")
    Call<UserRegisterResult> userRegister(@Body UserRegisterInput input);

    @POST("UserLogin_c/resetPassword")
    Call<ResetPasswordResult> resetPassword(@Body ResetPasswordInput input);

    @POST("GetChaters_c/getChaters")
    Call<GetMsgResult> Msg(@Body GetMsgInput input);

    @POST("CheckingMyMsg_c/checkingMyMsg")
    Call<GetMsgResult> checkingMyMsg(@Body GetMsgInput input);

    @POST("UserChat_c/userChat")
    Call<SendMsgResult> Send(@Body SendMsgInput input);

    @POST("UserChat_c/userChat")
    Call<UserChatResult> userChat(@Body UserChatInput input);

    @POST("CreateNewGroup_c/newGroupChatting")
    Call<NewGroupChattingResult> newGroupChatting(@Body NewGroupChattingInput input);

    @POST("GetUsers_c/getUsers")
    Call<GetUsersResult> getUsers();

    @POST("GetGroups_c/getGroups")
    Call<GetGroupsResult> getgroups(@Body GetGroupsInput input);

    @POST("Individualtask_c/assignInditask")
    Call<AssignIndiResult> assignindi(@Body AssignIndiInput input);

    @POST("Grouptask_c/assignGrouptask")
    Call<AssignGrpResult> assigngrp(@Body AssignGrpInput input);

    @POST("GetGroupFrom_c/getGroupTaskFrom")
    Call<GetMyTaskGRPResult> getgrptsk(@Body GetMyTaskGRPInput input);

    @POST("GetGroupTo_c/getGroupTaskTo")
    Call<GetTaskToMeGRPResult> gettasktome(@Body GetTaskToMeGRPInput input);

    @POST("GetIndiTo_c/getIndiTaskTo")
    Call<GetMyTaskINDIResult> getinditask(@Body GetMyTaskINDIInput input);

    @POST("GetIndiFrom_c/getIndiTaskFrom")
    Call<GetTaskToMeINDIResult> gettaskinditome(@Body GetTaskToMeINDIInput input);
}


