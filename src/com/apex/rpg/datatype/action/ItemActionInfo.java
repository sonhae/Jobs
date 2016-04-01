package com.apex.rpg.datatype.action;

import org.bukkit.inventory.ItemStack;

public class ItemActionInfo extends ActionInfo {

	private ItemStack item;
	public ItemStack getItem() {
		return item;
	}
	public ItemActionInfo(Actions action, ItemStack item) {
		super(action);
		this.item = item;
		// TODO Auto-generated constructor stub
	}

}
