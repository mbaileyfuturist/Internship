package internship.payweekExample;

import java.io.PrintStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;



public class PayweekExecutor {

	static final int beginYear = 1994;
	static final int endYear = 2099;
	static final boolean isPayweekEven = false;

	
	public static void printTimecard(PrintStream output, SummerTimecard card){
		Calendar id = card.getCutoffIndependenceDay();
		Calendar md = card.getCutoffMemorialDay();
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
		
		output.printf("%-38s (%10d)      %38s (%10d)\n", df.format(md.getTime()),
				    	md.getTimeInMillis() / 1000, df.format(id.getTime()),
				    	id.getTimeInMillis() / 1000 );
	}

	
	public static void main(String[] argv){

		ArrayList<SummerTimecard> timecards =  new ArrayList<SummerTimecard>(endYear - beginYear);

		for (int year = beginYear; year <= endYear; year++){
			SummerTimecard tc = new SummerTimecard(year, isPayweekEven);
			if (tc.hasPayweek(true)){
				timecards.add(tc);
			}
		}
		Collections.sort(timecards);
		
		for (SummerTimecard tc: timecards){
			printTimecard(System.out, tc);

		}
	}
	
}
