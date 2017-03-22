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
	private final int HosSize = 300; //医院病床数
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
//			System.out.println("请输入预先输入进医院的病人天数");
//			int day = mHosManager.reader.nextInt();
			try {
				mHosManager.prepare(200);
			} catch (MyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String bed = "请输入您的病种：（1.急诊 2.鼻窦炎 3.扁桃体炎 4.中耳炎）";
			String kinds = request.getParameterValues("kind")[0];
			int kind = Integer.parseInt(kinds);
			try {
				mHosManager.predict(kind);
//				mHosManager.working();

			}
			catch (MyException e) {
				// TODO: handle exception
				bed="您的ID号为"+patient.Tid;
				bed = bed + "<br/> 预测等"+mHosManager.getIntervalDays(patient.date,mHosManager.cal.getTime())+"天可入院";
			}
			catch (Exception e) {
				// TODO: handle exception
				bed="不能预测该病人出院时间";
			}

         request.setAttribute("ANSWER", bed);

         ServletContext context = getServletContext();
        
         RequestDispatcher dispatcher = context.getRequestDispatcher(target);
         dispatcher.forward(request,response);
	} 
	
	
	private void Initialize() {
		reader = new Scanner(System.in);
//		System.out.println("请输入医院规模");
//		int HosSize = reader.nextInt();
		mHospital = new Hospital(HosSize);
		cal = Calendar.getInstance();
	}

	private int getIntervalDays(Date fDate, Date oDate) {// 计算两个日期差
		if (null == fDate || null == oDate) {
			return -1;
		}
		long intervalMilli = oDate.getTime() - fDate.getTime();
		return (int) (intervalMilli / (24 * 60 * 60 * 1000));
	}
	
	private void prepare(int day) throws MyException{
		int  daynum, week_of_day;
//		System.out.println("您选择了预先输入"+day+"天病人");
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
//		System.out.println("预先输入结束");
//		mHospital.printBed();
	}
	
	private void predict(int kind) throws Exception {
		int daynum, week_of_day;
		daynum = cal.get(Calendar.DATE);
		week_of_day = cal.get(Calendar.DAY_OF_WEEK);
		Date Ddate = new Date();
		Ddate = cal.getTime();
		System.out.println(Ddate);
		System.out.println("请输入您的病种：（1.急诊 2.鼻窦炎 3.扁桃体炎 4.中耳炎）");
		while (kind > 4 || kind < 1) {
			// int kind = reader.nextInt();
			System.out.println("不允许输入其他病种");
			kind = reader.nextInt();
		}
		System.out.println("开始预测病人出院时间");
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
				throw new Exception("超过365天还不能出院");
		}
	}
		
}
