package beans;

public class Terms {
	private String name;
	private double natScore[];
	private double absoluteScore[];
	
	public Terms(String name, double[]natScore, double[]absoluteScore){
		this.name = name;
		this.natScore = natScore;
		this.absoluteScore = absoluteScore;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double[] getNatScore() {
		return natScore;
	}

	public void setNatScore(double[] natScore) {
		this.natScore = natScore;
	}

	public double[] getAbsoluteScore() {
		return absoluteScore;
	}

	public void setAbsoluteScore(double[] absoluteScore) {
		this.absoluteScore = absoluteScore;
	}
	
	
}
