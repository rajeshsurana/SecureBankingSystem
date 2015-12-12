package com.spring.util;

public class MerchantItems {

	public MerchantItems(String itemName, float itemCost, int itemId) {
		super();
		this.itemName = itemName;
		this.itemCost = itemCost;
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public float getItemCost() {
		return itemCost;
	}
	public void setItemCost(float itemCost) {
		this.itemCost = itemCost;
	}
	public MerchantItems() {
		super();
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	String itemName;
	float itemCost;
	int itemId;

}
