package com.pmaptechnotech.smartworkflow.models;

public class AssignIndiInput {
    public String indi_assign_from_id;
    public String indi_assign_to_id;
    public String indi_task_name;
    public String indi_task_deadline;

    public AssignIndiInput(String indi_assign_from_id, String indi_assign_to_id, String indi_task_name, String indi_task_deadline) {
        this.indi_assign_from_id = indi_assign_from_id;
        this.indi_assign_to_id = indi_assign_to_id;
        this.indi_task_name = indi_task_name;
        this.indi_task_deadline = indi_task_deadline;
    }
}
