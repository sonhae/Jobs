package com.apex.rpg.datatype.action;

import org.bukkit.entity.Entity;

public class EntityActionInfo extends ActionInfo{

	private Entity entity;
	
	public Entity getEntity() {
		return entity;
	}

	public EntityActionInfo(Actions action, Entity entity) {
		super(action);
		this.entity = entity;
		// TODO Auto-generated constructor stub
	}

}
