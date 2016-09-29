package hospitalizationEasy;

import java.util.*;

public class Queue {
	
	private int QueId;
	private LinkedList<patient> deque;
	
	public Queue(){}
	
	public Queue(int kind, LinkedList<patient> deque){
		this.QueId = kind;
		this.deque = deque;
	}
	
	public int getQueId(){
		return QueId;
	}
	
	public LinkedList<patient> getDeque(){
		return deque;
	}
	
	public void setQueId(int QueId){
		this.QueId = QueId;
	}
	
	public void outToQueue(patient p){//被踢出床位后进入队列
		deque.addFirst(p);
	}
	
	public patient popQueue(){//从队列重新被分配床位
		return deque.removeFirst();
	}
}
