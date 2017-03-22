package hospitalizationEasy;


public class Bed {
	private patient p;//该病床的病人
	boolean flag;//床位是否有病人
	private int dayout;//病人离出院的天数
	
	public Bed(){
		p = null;
		flag = false;
		dayout = 0;
	}
	
	public Bed(patient p, boolean flag, int dayout){
		this.p = p;
		this.flag = flag;
		this.dayout = dayout;
	}
	
	public patient getPatient(){
		return p;
	}
	
	public boolean getFlag(){
		return flag;
	}
	
	public int getDayout(){
		return dayout;
	}
	
	public void setPatient(patient p){
		this.p = p;
	}
	
	public void setFlag(boolean flag){
		this.flag = flag;
	}
	
	public void setDayout(int dayout){
		this.dayout = dayout;
	}
}
