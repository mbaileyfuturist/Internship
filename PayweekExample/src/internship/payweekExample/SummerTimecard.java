package internship.payweekExample;

import java.util.Calendar;

public class SummerTimecard implements Comparable {

	public final Calendar memorialDay;
	public final Calendar independenceDay;
	public final int year;
	
	public static enum SortOrder { FRIDAY, MONDAY, WEEKEND, OTHER };
	public final static int lastHourForSubmit = 12;
	public final static int normalSubmit = 16;
	
	private boolean isPayweekEven;
	
	SummerTimecard(int year, boolean isPayweekEven){
		this.year = year;
		Calendar cal = Calendar.getInstance();

		// Calculate the day of Memorial Day by
		// finding the first Monday in June, and
		// subtracting 1 week
		cal.clear(Calendar.HOUR_OF_DAY);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, Calendar.JUNE);
		cal.set(Calendar.WEEK_OF_MONTH, 1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.add(Calendar.WEEK_OF_YEAR, -1);
		memorialDay = cal;
	
		cal = Calendar.getInstance();
		cal.clear(Calendar.HOUR_OF_DAY);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, Calendar.JULY);
		cal.set(Calendar.DAY_OF_MONTH, 4);
		independenceDay = cal;

		this.isPayweekEven = isPayweekEven;
	}
	
	
	public void setIsPayweekEven(boolean isPayweekEven){
		this.isPayweekEven = isPayweekEven;
	}
	
	public boolean getIsPayweekEven(){
		return this.isPayweekEven;
	}
	
	public SortOrder sortOrder(){
		if (hasPayweek(this.isPayweekEven, this.independenceDay)){
			switch (this.independenceDay.get(Calendar.DAY_OF_WEEK)){
				case Calendar.FRIDAY:
					return SortOrder.FRIDAY;
				case Calendar.MONDAY:
					return SortOrder.MONDAY;
				case Calendar.SATURDAY:
				case Calendar.SUNDAY:
					return SortOrder.WEEKEND;
				default:
					;;
			}
		}
		return SortOrder.OTHER;
	}
	
    private Calendar getWorkdayBeforeMemorialDay(){
    	Calendar workday = this.memorialDay;
    	workday.add(Calendar.DAY_OF_MONTH, -3);
    	return workday;
    }
    
    private Calendar getWorkdayBeforeIndependenceDay(){
    	Calendar workday = this.independenceDay;
    	switch (this.independenceDay.get(Calendar.DAY_OF_WEEK)){
    		case Calendar.MONDAY:
       			workday.add(Calendar.DAY_OF_MONTH, -3);
    			break; 
    		case Calendar.SUNDAY:
       			workday.add(Calendar.DAY_OF_MONTH, -2);
    			break;
    		default:
    			workday.add(Calendar.DAY_OF_MONTH, -1);
    			break;
    	}
        return workday;
    }
    
    public boolean hasPayweek(){
    	return hasPayweek(this.isPayweekEven);
    }
    
    
    public boolean hasPayweek(boolean isPayweekEven){
    	return hasPayweek(isPayweekEven, this.memorialDay) ||
    			hasPayweek(isPayweekEven, this.independenceDay);
    	
    }
    
    private boolean hasPayweek(boolean isPayweekEven, Calendar cal){
    	if (cal.get(Calendar.WEEK_OF_YEAR) % 2 == 0){
    		return isPayweekEven;
    	}else{
    		return !isPayweekEven;
    	}
    }
    
    public Calendar getCutoffMemorialDay(){
    	return getCutoffMemorialDay(this.isPayweekEven);
    }
    
    public Calendar getCutoffMemorialDay(boolean isPayweekEven){
    	Calendar cutoff = this.getWorkdayBeforeMemorialDay();
    	int payweek = (this.isPayweekEven)? 0 : 1;
    	if ( (cutoff.get(Calendar.WEEK_OF_YEAR) % 2) == payweek){
    		cutoff.set(Calendar.HOUR_OF_DAY, lastHourForSubmit);
    	}else{
    		cutoff.add(Calendar.DAY_OF_YEAR, 7);
    		cutoff.set(Calendar.HOUR_OF_DAY, normalSubmit);
    	}
    	return cutoff;
    }
    
    
    public Calendar getCutoffIndependenceDay(){
    	return getCutoffIndependenceDay(this.isPayweekEven);
    }
    
    public Calendar getCutoffIndependenceDay(boolean isPayweekEven){
    	Calendar cutoff = this.getWorkdayBeforeIndependenceDay();
    	if (hasPayweek(isPayweekEven, cutoff) && (cutoff.get(Calendar.DAY_OF_WEEK) > Calendar.WEDNESDAY)){
    		cutoff.set(Calendar.HOUR_OF_DAY, lastHourForSubmit);
    	}else{
    		cutoff.add(Calendar.WEEK_OF_YEAR, 1);
    		switch (cutoff.get(Calendar.DAY_OF_WEEK)){
    			case Calendar.FRIDAY:
    				break;
    			default:
    				int delta = Calendar.FRIDAY - cutoff.get(Calendar.DAY_OF_WEEK);
    				cutoff.add(Calendar.DAY_OF_WEEK, delta);
    				break;
    		}
    		cutoff.set(Calendar.HOUR_OF_DAY, normalSubmit);
    	}
    	return cutoff;
    }


	public int compareTo(Object arg) {
		SummerTimecard other = (SummerTimecard) arg; 
		int cmp = this.sortOrder().compareTo(other.sortOrder());
		if (cmp == 0){
			return Integer.compare(other.year, this.year);
		}else{
			return cmp;
		}
	}
}
