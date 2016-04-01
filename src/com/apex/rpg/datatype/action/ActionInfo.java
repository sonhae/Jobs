package com.apex.rpg.datatype.action;

public abstract class ActionInfo {
	public enum Actions{
		BREAK, KILL, BREW, PLACE, FISH;
	}
	private Actions action;
	
	public ActionInfo(Actions action) {
		super();
		this.action = action;
	}
	
	public Actions getAction() {
		return action;
	}
	
}
