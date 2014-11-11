package beans;

public class Terms {
	private String name;
	private int natScore[];
	private double absoluteScore[];
	
	public Terms(String name, int[]natScore, double[]absoluteScore){
		this.name = name;
		this.natScore = natScore;
		this.absoluteScore = absoluteScore;
	}
	
	public Terms(String name){
		this.name = name;
		natScore = new int[3];
		absoluteScore = new double[3];
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int[] getNatScore() {
		return natScore;
	}

	public void setNatScore(int[] natScore) {
		this.natScore = natScore;
	}

	public double[] getAbsoluteScore() {
		return absoluteScore;
	}

	public void setAbsoluteScore(double[] absoluteScore) {
		this.absoluteScore = absoluteScore;
	}
	
	
}
