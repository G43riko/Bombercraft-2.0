package Bombercraft2.Bombercraft2.gui.submenu;

import Bombercraft2.Bombercraft2.gui.submenu.SubmenuItem.GAction;

import java.util.HashMap;

public class SubmenuRadioGroup {
    public interface Action {
        void run(SubmenuItem item);
    }

    private final HashMap<String, SubmenuItem> values       = new HashMap<>();
    private       Action                       action       = null;
    private       SubmenuItem                  selectedItem = null;

    public SubmenuRadioGroup() {this(null);}

    public SubmenuRadioGroup(Action action) {
        this.action = action;
    }

    public void addItem(String key, SubmenuItem item) {
        if (values.isEmpty()) {
            selectedItem = item;
        }
        values.put(key, item);
    }

    public SubmenuItem getSelectedItem() {
        return selectedItem;
    }

    public GAction getActionFor(SubmenuItem item) {
        return () -> {
            selectedItem = item;
            action.run(selectedItem);
            return false;
        };
    }

    public void setActive(SubmenuItem submenuItem) {
        selectedItem = submenuItem;
    }

}
