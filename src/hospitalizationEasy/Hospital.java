package hospitalizationEasy;


public class Hospital {

	private int remain_bed;//剩余床位数
	private static Bed[] beds;//医院病床列表
	public patients mPatients;
	private static int[][] before = new int[][] { { 1, 1, 1, 1, 1, 1, 1 }, { 2, 3, 2, 2, 2, 2, 3 },
		{ 2, 3, 2, 2, 2, 2, 3 }, { 1, 2, 1, 5, 4, 3, 2 } };
	
	public Hospital(int bednumber){
		remain_bed = bednumber;
		beds = new Bed[bednumber];
		InitialBed();
		mPatients = new patients();
	}
	
	
	public void InitialBed(){
		for(int i=0;i<remain_bed;i++){
			beds[i] = new Bed();
		}
	}
	
	
	public void updatehospital() {// 实现病人的出院和其他病人的住院时间-1
		for (Bed b :beds) {
			if (b.getFlag() == true) {// 病床有人 没人就直接跳过
				int dayout = b.getDayout();// 病人离出院的天数				

				if (dayout == 1) {// 病人出院
					//cured.add(hospital.getBed(i).getPatient()); 出院队列
					remain_bed++;// 医院空闲病床加1
					b.setFlag(false);// 病床状态改为空闲
				}
				else
					b.setDayout(--dayout);// 出院期限-1天
			}
		}		
	}
	
	
	public void printBed() {
		System.out.println("      flag PatientID Kind Dayout");
		for (int i = 0; i < beds.length; i++) {
			if (beds[i].flag) {// 病床不为空
				System.out.println("床号" + i + ": " + beds[i].flag + "    "
						+ beds[i].getPatient().id + "     " + beds[i].getPatient().kind
						+ "     " + beds[i].getDayout());
			} else {// 病床为空
				System.out.println("床号" + i + ": " + beds[i].flag + "   *    **    ***");
			}
		}
	}
	
	
	private void insert(patient p, int bednum) throws MyException {// 插入病人到床号为bednum的床位
		if (p.id==patient.Tid)
			throw new MyException(); 	
		beds[bednum].setPatient(p);
		beds[bednum].setFlag(true);
		beds[bednum].setDayout(p.getAfterSurgery() + p.getBeforeSurgery());
	}
	
	
	private int find(int kindid){//根据病种找到第一个可先腾出的床位或者找不到
		if(kindid==0){//存在空床
			for(int i=0;i<beds.length;i++){
				if(beds[i].getFlag()==false){
						return i;
				}
			}
		}
		else if (0<kindid&&kindid<5){// 不存在空床，找到可抢占的病床号
			for(int i=0;i<beds.length;i++){
				if((beds[i].getPatient().getKind()==kindid)//id匹配
					&&beds[i].getDayout()>beds[i].getPatient().getAfterSurgery())//而且还没手术
					return i;
			}
		}
		return 0;
	}
	
	
	private int[] setArgs(int week_of_day) {
		int[] args;
		if(week_of_day==5){// 周四
			args = new int[]{1, 4, 2, 3};
		}else if(week_of_day==6||week_of_day==7||week_of_day==1){
			args = new int[]{1, 4, 3, 2};// 周五、六、日
		}else{
			args = new int[]{1, 3, 2, 4};
		}
		return args;
	}
	
	
	public void allocate(int week_of_day) throws MyException{//插入病人到病床
		int bednum;//用于储存床位号
		int []args = setArgs(week_of_day);
		int q1size = mPatients.getQueueSize(args[0]);
		while(q1size!=0){
			if(remain_bed>0){
				bednum = find(0);//遍历并找到病床号
				patient tempp = mPatients.pop(args[0]);  //Q1.getDeque().pollFirst();					
				tempp.setBeforeSurgery(before[tempp.getKind()-1][week_of_day-1]);//设置术前时间
				insert(tempp,bednum);//插入病床
				remain_bed--;
//				tempRemain=hospital.getRemainBed();//新加的
//				hospital.setRemainBed(--tempRemain);//新加的
				q1size--;//insert并且q1size--并且弹出床位的病人插入回自己队列头部
			}
			else if(find(args[3])!=0){
				bednum = find(args[3]);//遍历并找到病床号
				patient tempp = mPatients.pop(args[0]);//病人出列
				tempp.setBeforeSurgery(before[tempp.getKind()-1][week_of_day-1]);//设置术前时间
				mPatients.push(args[3], beds[bednum].getPatient());//被踢病人插入队列
//				patient return_patient=hospital.getBed(bednum).getPatient();
//				Q4.outToQueue(return_patient);
				insert(tempp,bednum);//病人插入病床
				q1size--;
			}
			else if(find(args[2])!=0){
				bednum = find(args[2]);//遍历并找到病床号
				patient tempp = mPatients.pop(args[0]);
				tempp.setBeforeSurgery(before[tempp.getKind()-1][week_of_day-1]);//设置术前时间
				mPatients.push(args[2], beds[bednum].getPatient());
//				patient return_patient=hospital.getBed(bednum).getPatient();
//				Q3.outToQueue(return_patient);
				insert(tempp,bednum);
				q1size--;
			}
			else if(find(args[1])!=0)
			{
				bednum=find(args[1]);//遍历并找到病床号
				patient tempp = mPatients.pop(args[0]);
				tempp.setBeforeSurgery(before[tempp.getKind()-1][week_of_day-1]);//设置术前时间
				mPatients.push(args[1], beds[bednum].getPatient());
//				patient return_patient=hospital.getBed(bednum).getPatient();
//				Q2.outToQueue(return_patient);
				insert(tempp,bednum);
				q1size--;
			}//insert并且q1size--
			else
				return;//没有空床位或者可以弹出的床位
		}
		int q2size = mPatients.getQueueSize(args[1]);
		while(q2size!=0){
			if(remain_bed>0){
				bednum = find(0);//遍历并找到病床号
				patient tempp = mPatients.pop(args[1]);  //Q2.getDeque().pollFirst();					
				tempp.setBeforeSurgery(before[tempp.getKind()-1][week_of_day-1]);//设置术前时间
				insert(tempp,bednum);//插入病床
				remain_bed--;
				q2size--;//insert并且q1size--并且弹出床位的病人插入回自己队列头部
			}
			else if(find(args[3])!=0){
				bednum = find(args[3]);//遍历并找到病床号
				patient tempp = mPatients.pop(args[1]);//病人出列
				tempp.setBeforeSurgery(before[tempp.getKind()-1][week_of_day-1]);//设置术前时间
				mPatients.push(args[3], beds[bednum].getPatient());//被踢病人插入队列
//				patient return_patient=hospital.getBed(bednum).getPatient();
//				Q4.outToQueue(return_patient);
				insert(tempp,bednum);//病人插入病床
				q2size--;
			}
			else if(find(args[2])!=0){
				bednum = find(args[2]);//遍历并找到病床号
				patient tempp = mPatients.pop(args[1]);//病人出列
				tempp.setBeforeSurgery(before[tempp.getKind()-1][week_of_day-1]);//设置术前时间
				mPatients.push(args[2], beds[bednum].getPatient());//被踢病人插入队列
//				patient return_patient=hospital.getBed(bednum).getPatient();
//				Q3.outToQueue(return_patient);
				insert(tempp,bednum);//病人插入病床
				q2size--;
			}
			else
				return;//没有空床位或者可以弹出的床位
		}
		int q3size = mPatients.getQueueSize(args[2]);
		while(q3size!=0){
			if(remain_bed>0){
				bednum = find(0);//遍历并找到病床号
				patient tempp = mPatients.pop(args[2]);  //Q3.getDeque().pollFirst();					
				tempp.setBeforeSurgery(before[tempp.getKind()-1][week_of_day-1]);//设置术前时间
				insert(tempp,bednum);//插入病床
				remain_bed--;
				q3size--;//insert并且q1size--并且弹出床位的病人插入回自己队列头部
			}
			else if(find(args[3])!=0){
				bednum = find(args[3]);//遍历并找到病床号
				patient tempp = mPatients.pop(args[2]);//病人出列
				tempp.setBeforeSurgery(before[tempp.getKind()-1][week_of_day-1]);//设置术前时间
				mPatients.push(args[3], beds[bednum].getPatient());//被踢病人插入队列
//				patient return_patient=hospital.getBed(bednum).getPatient();
//				Q4.outToQueue(return_patient);
				insert(tempp,bednum);//病人插入病床
				q3size--;
			}					
			else
				return;//没有空床位或者可以弹出的床位
		}
		int q4size = mPatients.getQueueSize(args[3]);
		while(q4size!=0){
			if(remain_bed>0){
				bednum = find(0);//遍历并找到病床号
				patient tempp = mPatients.pop(args[3]);  //Q3.getDeque().pollFirst();					
				tempp.setBeforeSurgery(before[tempp.getKind()-1][week_of_day-1]);//设置术前时间
				insert(tempp,bednum);//插入病床
				remain_bed--;
				q4size--;//insert并且q1size--并且弹出床位的病人插入回自己队列头部
			}
			else
				return;//没有空床位或者可以弹出的床位
		}
	}
}
