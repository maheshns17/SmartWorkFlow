package com.pmaptechnotech.smartworkflow.models;

public class AssignGrpInput {
    public String grp_assign_from_id;
    public String grp_assign_to_id;
    public String grp_task_name;
    public String grp_task_deadline;

    public AssignGrpInput(String grp_assign_from_id, String grp_assign_to_id, String grp_task_name, String grp_task_deadline) {
        this.grp_assign_from_id = grp_assign_from_id;
        this.grp_assign_to_id = grp_assign_to_id;
        this.grp_task_name = grp_task_name;
        this.grp_task_deadline = grp_task_deadline;
    }
}