package Bombercraft2.Bombercraft2.gui.submenu;

import java.util.HashMap;

import Bombercraft2.Bombercraft2.gui.submenu.SubmenuItem.GAction;

public class SubmenuRadioGroup {
	public interface Action{
		public void run(SubmenuItem item);
	}
	private HashMap<String, SubmenuItem> 	values = new HashMap<String, SubmenuItem>(); 
	private Action 							action;
	private SubmenuItem 					selectedItem;
	
	public SubmenuRadioGroup() {this(null);}
	public SubmenuRadioGroup(Action action) {
		this.action = action;
	}
	
	public void addItem(String key, SubmenuItem item){
		if(values.isEmpty()){
			selectedItem = item;
		}
		values.put(key, item);
	}
	public SubmenuItem getSelectedItem(){
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
