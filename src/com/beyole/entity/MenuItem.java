package com.beyole.entity;

public class MenuItem {

	private boolean isSelcted;
	private String text;
	private int icon;
	private int iconSelected;

	public MenuItem() {
	}

	public MenuItem(boolean isSelcted, String text, int icon, int iconSelected) {
		this.isSelcted = isSelcted;
		this.text = text;
		this.icon = icon;
		this.iconSelected = iconSelected;
	}

	public boolean isSelcted() {
		return isSelcted;
	}

	public void setSelcted(boolean isSelcted) {
		this.isSelcted = isSelcted;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getIconSelected() {
		return iconSelected;
	}

	public void setIconSelected(int iconSelected) {
		this.iconSelected = iconSelected;
	}

}
