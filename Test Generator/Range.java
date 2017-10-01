
public class Range {
	private int min;
	private int max;
	
	public Range(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	public int val() {
		return (int) (Math.random() * (max - min) + min);
	}
}
