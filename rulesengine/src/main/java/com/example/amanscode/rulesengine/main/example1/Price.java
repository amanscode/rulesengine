package com.example.amanscode.rulesengine.main.example1;

public class Price {

	int actualPrice;
	int discountedPrice;
	int discount;

	public int getDiscount() {
		return discount;
	}

	public int getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(int actualPrice) {
		this.actualPrice = actualPrice;
	}

	public void setDiscountedPrice(int discount) {
		this.discount = discount;
		this.discountedPrice = actualPrice - (discount * (actualPrice/100));
	}

	public int getDiscountedPrice() {
		return discountedPrice;
	}

}
