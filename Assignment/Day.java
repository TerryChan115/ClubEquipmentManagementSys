public class Day implements Cloneable{

	private int year;
	private int month;
	private int day;

	private static final String MonthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";

	// Constructor
	public Day(int y, int m, int d) {
		this.year = y;
		this.month = m;
		this.day = d;
	}

	public Day(String sDay) {
		set(sDay);
	} // Constructor, simply call set(sDay)

	// check if a given year is a leap year
	static public boolean isLeapYear(int y) {
		if (y % 400 == 0)
			return true;
		else if (y % 100 == 0)
			return false;
		else if (y % 4 == 0)
			return true;
		else
			return false;
	}

	// check if y,m,d valid
	static public boolean valid(int y, int m, int d) {
		if (m < 1 || m > 12 || d < 1)
			return false;
		switch (m) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				return d <= 31;
			case 4:
			case 6:
			case 9:
			case 11:
				return d <= 30;
			case 2:
				if (isLeapYear(y))
					return d <= 29;
				else
					return d <= 28;
		}
		return false;
	}
	public void advance(int d) {
		this.day += d;
		while (!Day.valid(year, month, day)) {
			switch (this.month) {
				case 1:
				case 3:
				case 5:
				case 7:
				case 8:
				case 10:
				case 12:
					if (this.day > 31) {
						this.day -= 31;
						this.month += 1;
					}
					break;
				case 4:
				case 6:
				case 9:
				case 11:
					if (this.day > 30) {
						this.day -= 30;
						this.month += 1;
					}
					break;
				case 2:
					if (isLeapYear(this.year)) {
						if (this.day > 29) {
							this.day -= 29;
							this.month += 1;
						}
					} else {
						if (this.day > 28) {
							this.day -= 28;
							this.month += 1;
						}
					}
					break;
			}
			if (this.month > 12) {
				this.month -= 12;
				this.year += 1;
			}
		}
	}
	
	// Return a string for the day like dd MMM yyyy
	@Override
	public String toString() {
		return day + "-" + MonthNames.substring((month - 1) * 3, (month) * 3) + "-" + year; // (month-1)*3,(month)*3
	}

	public void set(String sDay) // Set year,month,day based on a string like 01-Mar-2024
	{
		String[] sDayParts = sDay.split("-");
		this.day = Integer.parseInt(sDayParts[0]);
		this.year = Integer.parseInt(sDayParts[2]);
		this.month = MonthNames.indexOf(sDayParts[1]) / 3 + 1;
	}

	@Override
	public Day clone() {
		Day copy = null;
		try{
			copy = (Day) super.clone();
		} catch (CloneNotSupportedException a){
			a.printStackTrace();
		}

		return copy;
	}

	public static int compare(Day theday, Day another){
		if(theday.year > another.year){
			return 1;
		}
		else if(theday.year == another.year){
			if(theday.month > another.month){
				return 1;
			}
			else if(theday.month == another.month){
				if(theday.day > another.day){
					return 1;
				}
				else if(theday.day == another.day){
					return 0;
				}
				else{
					return -1;
				}
			}
			else{
				return -1;
			}
		}
		else{
			return -1;
		}
	}
}
