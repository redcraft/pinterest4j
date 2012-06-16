package ru.redcraft.pinterest4j;

public enum GiftPriceCategory {
	
	GIFTS_1_20(1, 20),
	GIFTS_20_50(20, 50),
	GIFTS_50_100(50, 100),
	GIFTS_100_200(100, 200),
	GIFTS_200_500(200, 500),
	GIFTS_500_MORE(500, 1000000);
	
	private final int from;
	private final int to;
	
	private GiftPriceCategory(int from, int to) {
		this.from = from;
		this.to = to;
	}

	public int getFrom() {
		return from;
	}

	public int getTo() {
		return to;
	}
}
