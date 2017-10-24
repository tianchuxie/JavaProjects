package ElevatorPackage;

/* this is not used */

public class Time {
	private int hour, min, sec;
	public Time (int h, int m, int s){
		hour = h;
	     min = m;
		sec = s;
	}
	
	public Time (Time t){
		hour = t.getH();
		min = t.getM();
		sec = t.getS();
	}
	
	
	public void addSec (int s){
		sec += s;
		if (sec >= 60) {
			min += 1;
			sec = 0;
			if (min >= 60){
				hour += 1;
				min = 0;
				if (hour == 24){
					hour =0;
				}
			}
		}
	}
	
	public int getH(){
		return hour;
	}
	
	public int getM(){
		return min;
	}
	
	public int getS(){
		return sec;
	}
	
	
	public int compareTo(Time t){
		int h = t.getH();
		if (hour > t.getH()){
			return 1;
		} else if (hour == t.getH() &&  min > t.getM()){
			return 1;
		} else if (hour == t.getH() && min == t.getM() &&  sec > t.getS()){
			return 1;
		} else if (hour == t.getH() && min == t.getM() &&  sec == t.getS()){
			return 0;
		} else {
			return -1;
		}
		
		
	}
	
	
	/*I assuming it right, but should try errors*/
	public int minus(Time t){
		if (sec > t.getS()){
			return sec - t.getS();
		}
		return sec + 60 - t.getS();
	}
	
	public String toString(){
		String h = "";
		String m = "";
		String s = "";
		if (this.hour < 10){
			h = "0";
		}
		if (this.min < 10) {
			m = "0";
		}
		
		if (this.sec < 10){
			s = "0";
		}
		
		return h + this.hour +":" + m + this.min + ":" + s + this.sec;
	}

	

}
