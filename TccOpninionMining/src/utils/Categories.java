package utils;

public enum Categories {
	POSITIVE("Positivo", 0,"positive"), NEGATIVE("Negativo", 1, "negative"), NEUTRAL("neutro", 2, "neutral");
	
	public String categoryName;
	public int code;
	public String categoryNameEN;
	
	private Categories(String categoryName, int code, String categoryNameEN) {
		this.categoryName = categoryName;
		this.code = code;
		this.categoryNameEN = categoryNameEN;
	}
	
	public String getCategoryName(){
		return this.categoryName;
	}
	
	public int getCode(){
		return this.code;
	}
	
	public String getCategoryNameEN(){
		return this.categoryNameEN;
	}
	
	
}
