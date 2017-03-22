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

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class searchServlet extends HttpServlet {
	
	private Scanner reader;
	private Hospital mHospital;
	private final int HosSize = 300; //ҽԺ������
	private Calendar cal;

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
        
		 searchServlet mHosManager = new searchServlet();
			mHosManager.Initialize();
//			System.out.println("������Ԥ�������ҽԺ�Ĳ�������");
//			int day = mHosManager.reader.nextInt();
			try {
				mHosManager.prepare(200);
			} catch (MyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String bed = "���������Ĳ��֣���1.���� 2.����� 3.�������� 4.�ж��ף�";
			String kinds = request.getParameterValues("kind")[0];
			int kind = Integer.parseInt(kinds);
			try {
				mHosManager.predict(kind);
//				mHosManager.working();

			}
			catch (MyException e) {
				// TODO: handle exception
				bed="����ID��Ϊ"+patient.Tid;
				bed = bed + "<br/> Ԥ���"+mHosManager.getIntervalDays(patient.date,mHosManager.cal.getTime())+"�����Ժ";
			}
			catch (Exception e) {
				// TODO: handle exception
				bed="����Ԥ��ò��˳�Ժʱ��";
			}

         request.setAttribute("ANSWER", bed);

         ServletContext context = getServletContext();
        
         RequestDispatcher dispatcher = context.getRequestDispatcher(target);
         dispatcher.forward(request,response);
	} 
	
	
	private void Initialize() {
		reader = new Scanner(System.in);
//		System.out.println("������ҽԺ��ģ");
//		int HosSize = reader.nextInt();
		mHospital = new Hospital(HosSize);
		cal = Calendar.getInstance();
	}

	private int getIntervalDays(Date fDate, Date oDate) {// �����������ڲ�
		if (null == fDate || null == oDate) {
			return -1;
		}
		long intervalMilli = oDate.getTime() - fDate.getTime();
		return (int) (intervalMilli / (24 * 60 * 60 * 1000));
	}
	
	private void prepare(int day) throws MyException{
		int  daynum, week_of_day;
//		System.out.println("��ѡ����Ԥ������"+day+"�첡��");
		while(day!=0) {
			daynum = cal.get(Calendar.DATE);
			week_of_day = cal.get(Calendar.DAY_OF_WEEK);
			Date Ddate = new Date();
			Ddate = cal.getTime();
//			System.out.println(Ddate);			
			mHospital.mPatients.Arrival(0, Ddate);			
			mHospital.updatehospital();
			mHospital.allocate(week_of_day);			
			cal.set(Calendar.DATE, ++daynum);
			day--;
		}
//		System.out.println("Ԥ���������");
//		mHospital.printBed();
	}
	
	private void predict(int kind) throws Exception {
		int daynum, week_of_day;
		daynum = cal.get(Calendar.DATE);
		week_of_day = cal.get(Calendar.DAY_OF_WEEK);
		Date Ddate = new Date();
		Ddate = cal.getTime();
		System.out.println(Ddate);
		System.out.println("���������Ĳ��֣���1.���� 2.����� 3.�������� 4.�ж��ף�");
		while (kind > 4 || kind < 1) {
			// int kind = reader.nextInt();
			System.out.println("������������������");
			kind = reader.nextInt();
		}
		System.out.println("��ʼԤ�ⲡ�˳�Ժʱ��");
		mHospital.mPatients.Arrival(kind, Ddate);
		cal.set(Calendar.DATE, ++daynum);
		int i = 0;
		while (true) {
			i++;
			mHospital.mPatients.Arrival(0, Ddate);
			mHospital.updatehospital();
			mHospital.allocate(week_of_day);
			cal.set(Calendar.DATE, ++daynum);
			if (i > 365)
				throw new Exception("����365�컹���ܳ�Ժ");
		}
	}
		
}
