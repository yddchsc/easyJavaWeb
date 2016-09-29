package hospitalizationEasy;
public class Hospital {//医院类

	private int remain_bed;
	private Bed[] bed;
	
	public Hospital(){}
	
	public Hospital(int remain_bed,Bed[] bed){
		this.remain_bed = remain_bed;
		this.bed = bed;
	}
	
	public int getRemainBed(){
		return remain_bed;
	}
	
	public Bed getBed(int bednum){
		return bed[bednum];
	}
	
	public void setRemainBed(int remain_bed){
		this.remain_bed = remain_bed;
	}
	
}

