
public class SodaCan {
	
	private String type;
	private boolean opened;
	private int amount;
	
	public static void main(String[] args){
		SodaCan sc = new SodaCan("Dr. Pepper");
		sc.gulp();
		sc.gulp();
		sc.gulp();
		sc.gulp();
		sc.sip();
		int removed = sc.gulp();
		System.out.println(removed);
	}
	
	public SodaCan(String sodaType){
		this.type = sodaType;
		this.opened = false;
		this.amount = 250;
	}

	public void openCan(){
		this.opened = true;
	}
	
	public int sip(){
		if(this.opened){
			if (this.amount > 0){
				int newAmount = this.amount - 10;
				if (newAmount < 0){
					this.amount = 0;
					return 10 + newAmount;
				} else {
					this.amount = newAmount;
					return 10;
				}
			}
			return 0;
		}
		return 0;
	}
	
	public int gulp(){
		if(this.opened){
			if (this.amount > 0){
				int newAmount = this.amount - 50;
				if (newAmount < 0){
					this.amount = 0;
					return 50 + newAmount;
				} else {
					this.amount = newAmount;
					return 50;
				}
			}
			return 0;
		}
		return 0;
	}
	
	@Override
	public String toString(){
		return "SodaCan Type: " + this.type + " Opened: " + this.opened + " Amount Remaining: " + this.amount;
	}
	
}
