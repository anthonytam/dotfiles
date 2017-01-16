
public class Person {
	private enum Status {
		SATISFIED,
		THIRSTY, 
		VERY_THIRSTY
	}
	
	private Status thirstStatus;
	private int amountDrunk;
	
	public Person(){
		this.thirstStatus = Status.VERY_THIRSTY;
		this.amountDrunk = 0;
	}
	
	public void takeSip(SodaCan sc){
		this.amountDrunk += sc.sip();
		this.evaluateThirstStatus();
	}
	
	public void takeGulp(SodaCan sc){
		this.amountDrunk += sc.gulp();
		this.evaluateThirstStatus();
	}
	
	private void evaluateThirstStatus(){
		if (this.amountDrunk < 175)
			this.thirstStatus = Status.VERY_THIRSTY;
		else if (this.amountDrunk >= 175 & this.amountDrunk < 375)
			this.thirstStatus = Status.THIRSTY;
		else
			this.thirstStatus = Status.SATISFIED;
	}
	
	@Override
	public String toString(){
		return "Person Status: " + this.thirstStatus + " Amount Drunk: " + this.amountDrunk;
	}
	
	public static void main(String[] args) {
		SodaCan sc = new SodaCan("Dr. Pepper");
		Person me = new Person();
		System.out.println(me.toString());
		me.takeGulp(sc);
		System.out.println(me.toString());
		sc.openCan();
		me.takeGulp(sc);
		System.out.println(me.toString());
		me.takeGulp(sc);
		me.takeGulp(sc);
		me.takeGulp(sc);
		System.out.println(me.toString());
		sc = new SodaCan("Diet Coke");
		System.out.println(me.toString());
		me.takeGulp(sc);
		System.out.println(me.toString());
		sc.openCan();
		me.takeGulp(sc);
		System.out.println(me.toString());
		me.takeGulp(sc);
		me.takeGulp(sc);
		me.takeGulp(sc);
		System.out.println(me.toString());
		
		sc = new SodaCan("Dr. Pepper");
		me = new Person();
		System.out.println(me.toString());
		me.takeGulp(sc);
		System.out.println(me.toString());
		sc.openCan();
		me.takeGulp(sc);
		System.out.println(me.toString());
		me.takeGulp(sc);
		me.takeGulp(sc);
		me.takeGulp(sc);
		System.out.println(me.toString());
		sc = new SodaCan("Diet Coke");
		System.out.println(me.toString());
		me.takeGulp(sc);
		System.out.println(me.toString());
		sc.openCan();
		me.takeGulp(sc);
		System.out.println(me.toString());
		me.takeGulp(sc);
		me.takeGulp(sc);
		me.takeGulp(sc);
		System.out.println(me.toString());
	}

}
