package hospitalizationEasy;

import java.util.Date;
public class patient {
	 int id=0;
     int kind=0;//病人种类：1.急诊2.鼻窦炎3.扁桃体炎4.中耳炎
     int beforeSurgery=0;//术前准备时间
     int AfterSurgery=0;//术后观察时间
     static int Tid =0;
    static Date date;
    public patient(){}
    
    public patient(int id,int kind,int beforeSurgery,int afterSurgery){
        this.AfterSurgery=afterSurgery;
        this.kind=kind;
        this.beforeSurgery=beforeSurgery;
        this.id=id;
    }

    public int getAfterSurgery() {
        return AfterSurgery;
    }

    public int getBeforeSurgery() {
        return beforeSurgery;
    }

    public int getId() {
        return id;
    }

    public int getKind() {
        return kind;
    }
    
    public void setBeforeSurgery(int beforeSurgery) {
        this.beforeSurgery = beforeSurgery;
    }

}
