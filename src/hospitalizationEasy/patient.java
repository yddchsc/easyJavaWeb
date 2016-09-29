package hospitalizationEasy;

	public class patient {
	    private int id=0;
	    private int kind=0;
	    private int beforeSurgery=0;
	    private int AfterSurgery=0;
	   public patient(int id,int kind ,int beforeSurgery,int afterSurgery){
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
	    public void setbeforesurgery(int before){
	    	this.beforeSurgery=before;
	    }
	}

