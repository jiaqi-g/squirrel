package ucla.lucene;

public class wordSimBean {
	private int id;
	private String word_x;
	private String word_y;
	private Double sim;
	
	
	
	public wordSimBean(String word_x, String word_y, Double sim) {
		super();
		this.word_x = word_x;
		this.word_y = word_y;
		this.sim = sim;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWord_x() {
		return word_x;
	}
	public void setWord_x(String word_x) {
		this.word_x = word_x;
	}
	public String getWord_y() {
		return word_y;
	}
	public void setWord_y(String word_y) {
		this.word_y = word_y;
	}
	public Double getSim() {
		return sim;
	}
	public void setSim(Double sim) {
		this.sim = sim;
	}
	
	public String toString(){
		return this.word_x+" "+this.word_y+" "+this.sim;
	}
}
