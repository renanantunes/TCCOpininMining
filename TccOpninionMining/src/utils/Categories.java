package utils;

public enum Categories {
	POSITIVE("Positivo", 0,"positive", 2), NEGATIVE("Negativo", 1, "negative", 0), NEUTRAL("neutro", 2, "neutral", 1);
	
	public String categoryName;
	public int code;
	public String categoryNameEN;
	public int APICode;
	
	private Categories(String categoryName, int code, String categoryNameEN, int APICode) {
		this.categoryName = categoryName;
		this.code = code;
		this.categoryNameEN = categoryNameEN;
		this.APICode = APICode;
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
	
	public int getAPICode(){
		return this.APICode;
	}
	
}
