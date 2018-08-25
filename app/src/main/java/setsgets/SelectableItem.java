package setsgets;

public class SelectableItem  extends Items{
    private boolean isSelected = true;


    public SelectableItem(Items item,boolean isSelected) {
        super(item.getName(),item.getSurname());
        this.isSelected = isSelected;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}