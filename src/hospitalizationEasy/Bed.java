package hospitalizationEasy;

public class Bed {
	private patient p;
	private boolean flag;
	private int dayout;//病人还有还有多少天离开医院
	
	public Bed(){}
	
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



