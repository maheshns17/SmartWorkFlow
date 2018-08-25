package setsgets;

public class RegisterUsers {
    public String user_id;
    public String user_name;
    public String user_mobile;
    public String user_password;
    public String user_status;
    public boolean isSelected=false;

    public String getUser_id() {
        return user_id;
    }


    public String getUser_name() {
        return user_name;
    }


    public String getUser_mobile() {
        return user_mobile;
    }

    public String getUser_password() {
        return user_password;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setText(String text) {
        this.user_name = text;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}