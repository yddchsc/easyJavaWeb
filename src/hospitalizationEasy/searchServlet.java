package hospitalizationEasy;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class searchServlet extends HttpServlet {
	
	static int patient_id=0;
	public static int id=0;
	//public static Calendar Cdate=null;
	private static Map<Integer,patient> patientmap=null;
	private static List<patient> cured=null;
	private static Queue Q1 = new Queue(1,new LinkedList<patient>());
	private static Queue Q2 = new Queue(2,new LinkedList<patient>());
	private static Queue Q3 = new Queue(3,new LinkedList<patient>());
	private static Queue Q4 = new Queue(4,new LinkedList<patient>());
	private static int [][] before=null;
	public static int[] daylypatient = {0,0,0,0};//1.����2.�����3.��������4.�ж���
	private static final int ALLBEDNUM = 20;//ҽԺ��������
	private static int remain_bed = ALLBEDNUM;//ʣ��ɷ��䲡����
	private static Bed[] bed = new Bed[remain_bed+1];
	private static Hospital hospital = new Hospital(remain_bed,bed);
	private static Calendar cal = Calendar.getInstance();
	private static int day_of_week;
	//private static Date Ddate=new Date();;
	
	private String target = "/index.jsp";

	/**
	 * Constructor of the object.
	 */
	public searchServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         response.getWriter().write("Hello, world!");
         doPost(request,response);
  }   
 
 
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
	  	InitialBed();//��ʼ������
		//System.out.println(day_of_week);
		before=new int[][]{{1,1,1,1,1,1,1},{2,3,2,2,2,2,3},{2,3,2,2,2,2,3},{1,2,1,5,4,3,2}};
		//cured.clear();//��ʼ����
		//����daylypatient����
		//Cdate=Calendar.getInstance();
		Scanner reader = new Scanner(System.in);				
		int option,daynum;
		cured=new ArrayList<patient>();			
			daynum=cal.get(Calendar.DATE);
			day_of_week=cal.get(Calendar.DAY_OF_WEEK);
			Date Ddate=new Date();
			Ddate=cal.getTime();
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

			System.out.println(Ddate);
			patientmap = Create_Newpatient(daylypatient);
			for(int i=0;i<=3;i++){
				System.out.print(daylypatient[i]+" ");
			}
			
			insert_to_Deque(patientmap);
			
			//���Զ�������
//			System.out.println(Q1);
//			System.out.println(Q2);
//			System.out.println(Q3);
//			System.out.println(Q4);
			
			updatehospital();//����ҽԺ
			if(day_of_week==5){//����
				allocate(Q1,Q4,Q2,Q3);
			}else if(day_of_week==6||day_of_week==7||day_of_week==1){
				allocate(Q1,Q4,Q3,Q2);//���塢������
			}else{
				allocate(Q1,Q3,Q2,Q4);
			}
		
			String bed=Ddate+ "<br/>1/��֢��2/����ף�3/�����壬4/�ж���" + "<br/>" + "<table border='1'><tr><th> </th><th align='center'>status</th><th align='center'>����ID</th><th align='center'>����</th><th align='center'>ʣ��ʱ��</th></tr>";
			for(int i=1;i<=ALLBEDNUM;i++){
				if(hospital.getBed(i).getFlag()){//������Ϊ��
					bed = bed + "<tr><td align='center'>����"+i+" </td><td align='center'>"+hospital.getBed(i).getFlag()+"</td><td align='center'>"+hospital.getBed(i).getPatient().getId()+
							"</td><td align='left'>"+hospital.getBed(i).getPatient().getKind()+"</td><td align='center'>"+hospital.getBed(i).getDayout()+"</td></tr>";
				}else{//����Ϊ��
					bed = bed + "<tr><td align='left'>����"+i+" </td><td align='center'>"+hospital.getBed(i).getFlag()+"</td><td align='center'>*</td><td>**</td><td align='center'>***"+"</td></tr>";
				}
			}
			bed = bed + "</table>";
			
			cal.set(Calendar.DATE, ++daynum);

         request.setAttribute("ANSWER", bed);

         ServletContext context = getServletContext();
        
         RequestDispatcher dispatcher = context.getRequestDispatcher(target);
         dispatcher.forward(request,response);
  } 
	public static void InitialBed(){
		for(int i=1;i<=ALLBEDNUM;i++){
			bed[i] = new Bed();
		}
	}
	
	public static int getDate(Calendar cal){
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	public static double P_rand(double Lamda){ // ���ɷֲ�����4���²���
		double x=0,b=1,c=Math.exp(-Lamda),u; 
		do {
			u=Math.random();
			b *=u;
			if(b>=c)
				x++;
		}while(b>=c);
			return x;
	}

	public static  Map<Integer,patient> Create_Newpatient(int[] args){//�õ�4�ֲ�����ס����
		for(int i = 0;i<=3;i++){
        	int temp= (int)P_rand(1.1);
        	args[i]=temp+1;               
        }
		daylypatient = args;
        patientmap =new HashMap<Integer,patient>();
        for(int i=1;i<=args[0];i++){
        	patient_id++;
        	patientmap.put(patient_id,new patient(patient_id,1,0,6));
        }
        for(int i=1;i<=args[1];i++){
        	patient_id++;
        	patientmap.put(patient_id,new patient(patient_id,2,0,7));
        }
        for(int i=1;i<=args[2];i++){
        	patient_id++;
        	patientmap.put(patient_id,new patient(patient_id,3,0,10));
        }
        for(int i=1;i<=args[3];i++){
        	patient_id++;
        	patientmap.put(patient_id,new patient(patient_id,4,0,14));
        }
        return patientmap;
	}
	
	public static void insert(patient p, int bednum){//���벡�˵�����Ϊbednum�Ĵ�λ
		hospital.getBed(bednum).setPatient(p);
		hospital.getBed(bednum).setFlag(true);
		hospital.getBed(bednum).setDayout(p.getAfterSurgery()+p.getBeforeSurgery());

	}
	
	public static void insert_to_Deque( Map<Integer,patient> patientmap){
		for(Entry<Integer, patient> entry:patientmap.entrySet()){ 
			int kind=entry.getValue().getKind();
		     if(kind==1){
		    	 Q1.getDeque().addLast(entry.getValue());//β���������1
		     }
		     else if(kind==2){
		    	 Q2.getDeque().addLast(entry.getValue());//β���������2
		     }
		     else if(kind==3){
		    	 Q3.getDeque().addLast(entry.getValue());//β���������3
		     }
		     else if(kind==4){
		    	 Q4.getDeque().addLast(entry.getValue());//β���������4
		     }
		}   
	}
	public static int find(int kindid){//���ݲ����ҵ���һ�������ڳ��Ĵ�λ�����Ҳ���
		if(kindid==0){
			for(int i=1;i<=ALLBEDNUM;i++){
				if(hospital.getBed(i).getFlag()==false){
						return i;
				}
			
			}
		}
		//�����ҵ��մ��Ų��ҷ���
		
		else if (0<kindid&&kindid<5){// �ҵ�����������
			for(int i=1;i<=ALLBEDNUM;i++){
				if((hospital.getBed(i).getPatient().getKind()==kindid)//idƥ��
					&&hospital.getBed(i).getDayout()>hospital.getBed(i).getPatient().getAfterSurgery())//���һ�û����
					return i;
			}
		}
		return 0;
		
	}
	
	public static void updatehospital(){//ʵ�ֲ��˵ĳ�Ժ���������˵�סԺʱ��-1
		for(int i=1;i<=ALLBEDNUM;i++){
			if(hospital.getBed(i).getFlag()==true){//��������   û�˾�ֱ������
				int dayout=hospital.getBed(i).getDayout();
				int remainbed = hospital.getRemainBed();
			
				if(dayout==1){
					cured.add(hospital.getBed(i).getPatient());//ǰһ���dayout==1�����Խ���dayout==0�ͳ�Ժ��
					hospital.setRemainBed(++remainbed);
					hospital.getBed(i).setFlag(false);
				}
				else
					hospital.getBed(i).setDayout(--dayout);//��Ժ����-1��
			}
		}
	}
	public static void allocate(Queue Q1,Queue Q2,Queue Q3,Queue Q4){//���벡�˵�����
		int bednum;//���ڴ��洲λ��
		int tempRemain;//������Ĵ�λ������
		int q1size=Q1.getDeque().size();
		while(q1size!=0){
			if(hospital.getRemainBed()>0){
				bednum=find(0);//�������ҵ�������
				patient tempp=Q1.getDeque().pollFirst();					
				tempp.setbeforesurgery(before[tempp.getKind()-1][day_of_week-1]);//������ǰʱ��
				insert(tempp,bednum);
				tempRemain=hospital.getRemainBed();//�¼ӵ�
				hospital.setRemainBed(--tempRemain);//�¼ӵ�
				q1size--;//insert����q1size--���ҵ�����λ�Ĳ��˲�����Լ�����ͷ��
			}
			else if(find(Q4.getQueId())!=0){
				bednum=find(Q4.getQueId());//�������ҵ�������
				patient tempp=Q1.getDeque().pollFirst();					
				tempp.setbeforesurgery(before[tempp.getKind()-1][day_of_week-1]);//������ǰʱ��
				patient return_patient=hospital.getBed(bednum).getPatient();
				Q4.outToQueue(return_patient);
				insert(tempp,bednum);
				q1size--;
			}
				//insert����q1size--
			else if(find(Q3.getQueId())!=0){
				bednum=find(Q3.getQueId());//�������ҵ�������
				patient tempp=Q1.getDeque().pollFirst();
				tempp.setbeforesurgery(before[tempp.getKind()-1][day_of_week-1]);//������ǰʱ��
				patient return_patient=hospital.getBed(bednum).getPatient();
				Q3.outToQueue(return_patient);
				insert(tempp,bednum);
				q1size--;//insert����q1size--
			}
			else if(find(Q2.getQueId())!=0)
			{
				bednum=find(Q2.getQueId());//�������ҵ�������
				patient tempp=Q1.getDeque().pollFirst();
				tempp.setbeforesurgery(before[tempp.getKind()-1][day_of_week-1]);//������ǰʱ��
				patient return_patient=hospital.getBed(bednum).getPatient();
				Q2.outToQueue(return_patient);
				insert(tempp,bednum);
				q1size--;//insert����q1size--
			}//insert����q1size--
			else
				return;//û�пմ�λ���߿��Ե����Ĵ�λ
		}
		int q2size=Q2.getDeque().size();
		while(q2size!=0){
			if(hospital.getRemainBed()>0){
				bednum=find(0);//�������ҵ�������
				patient tempp=Q2.getDeque().pollFirst();
				tempp.setbeforesurgery(before[tempp.getKind()-1][day_of_week-1]);//������ǰʱ��
				tempRemain=hospital.getRemainBed();//�¼ӵ�
				hospital.setRemainBed(--tempRemain);//�¼ӵ�
				insert(tempp,bednum);
				q2size--;//insert����q2size--���ҵ�����λ�Ĳ��˲�����Լ�����ͷ��
			}
			else if(find(Q4.getQueId())!=0){
				bednum=find(Q4.getQueId());//�������ҵ�������
				patient tempp=Q2.getDeque().pollFirst();					
				tempp.setbeforesurgery(before[tempp.getKind()-1][day_of_week-1]);//������ǰʱ��
				patient return_patient=hospital.getBed(bednum).getPatient();
				Q4.outToQueue(return_patient);
				insert(tempp,bednum);
				q2size--;//insert����q2size--
			}
			else if(find(Q3.getQueId())!=0){
				bednum=find(Q3.getQueId());//�������ҵ�������
				patient tempp=Q2.getDeque().pollFirst();					
				tempp.setbeforesurgery(before[tempp.getKind()-1][day_of_week-1]);//������ǰʱ��
				patient return_patient=hospital.getBed(bednum).getPatient();
				Q3.outToQueue(return_patient);
				insert(tempp,bednum);
				q2size--;//insert����q2size--
			}	
			else
				return;//û�пմ�λ���߿��Ե����Ĵ�λ
		}
		int q3size=Q3.getDeque().size();
		while(q3size!=0){
			if(hospital.getRemainBed()>0){
				bednum=find(0);//�������ҵ�������
				patient tempp=Q3.getDeque().pollFirst();					
				tempp.setbeforesurgery(before[tempp.getKind()-1][day_of_week-1]);//������ǰʱ��
				insert(tempp,bednum);
				tempRemain=hospital.getRemainBed();//�¼ӵ�
				hospital.setRemainBed(--tempRemain);//�¼ӵ�
				q3size--;//insert����q1size--���ҵ�����λ�Ĳ��˲�����Լ�����ͷ��
			}
			else if(find(Q4.getQueId())!=0){
				bednum=find(Q4.getQueId());//�������ҵ�������
				patient tempp=Q3.getDeque().pollFirst();					
				tempp.setbeforesurgery(before[tempp.getKind()-1][day_of_week-1]);//������ǰʱ��
				patient return_patient=hospital.getBed(bednum).getPatient();
				Q3.outToQueue(return_patient);
				insert(tempp,bednum);
				q3size--;//insert����q3size--	
			}						
			else
				return;//û�пմ�λ���߿��Ե����Ĵ�λ
		}
		int q4size=Q4.getDeque().size();
		while(q4size!=0){
			if(hospital.getRemainBed()>0){
				bednum=find(0);//�������ҵ�������
				patient tempp=Q4.getDeque().pollFirst();					
				tempp.setbeforesurgery(before[tempp.getKind()-1][day_of_week-1]);//������ǰʱ��
				insert(tempp,bednum);
				tempRemain=hospital.getRemainBed();//�¼ӵ�
				hospital.setRemainBed(--tempRemain);//�¼ӵ�
				q4size--;//insert����q1size--���ҵ�����λ�Ĳ��˲�����Լ�����ͷ��
			}
			else
				return;//û�пմ�λ���߿��Ե����Ĵ�λ
		}
	}
	
	public static void printBed(){
		System.out.println("      flag PatientID Kind Dayout");
		for(int i=1;i<=ALLBEDNUM;i++){
			if(hospital.getBed(i).getFlag()){//������Ϊ��
				System.out.println("����"+i+": "+hospital.getBed(i).getFlag()+"    "+hospital.getBed(i).getPatient().getId()+
						"     "+hospital.getBed(i).getPatient().getKind()+"     "+hospital.getBed(i).getDayout());
			}else{//����Ϊ��
				System.out.println("����"+i+": "+hospital.getBed(i).getFlag()+"   *    **    ***");
			}
		}
	}

}
