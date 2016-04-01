package com.apex.rpg.datatype.action;

import org.bukkit.block.Block;

public class BlockActionInfo extends ActionInfo{

	private Block block;
	private boolean data;
	

	public BlockActionInfo(Actions action, Block block) {
		super(action);
		this.block = block;
		this.data = false;
		// TODO Auto-generated constructor stub
	}
	
	public boolean needData(){
		return data;
	}
	
	public void setData(boolean data) {
		this.data = data;
	}


	public Block getBlock() {
		return block;
	}

}
