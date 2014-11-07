package utils;

public enum Categories {
	POSITIVE("Positivo", 0), NEGATIVE("Negativo", 1), NEUTRAL("neutro", 2);
	
	public String categoryName;
	public int code;
	
	private Categories(String categoryName, int code) {
		this.categoryName = categoryName;
		this.code = code;
	}
	
	public String getCategoryName(){
		return this.categoryName;
	}
	
	public int getCode(){
		return this.code;
	}
	
	
}
