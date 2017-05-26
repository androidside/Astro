package src;

import java.util.Calendar;
import java.util.TimeZone;


public class main {
	
	public static double raDegrees = 279.38345;
	public static double decDegrees = 38.8035;
	
	public static double latitude = 31.779933; 
	public static double longitude = -95.717039;
		


	public static void main( String[] args ) {
		
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

		//Just for Testing
		Location l = new Location(latitude, longitude, 0); //0 = UTC timezone	
		int hours = now.get(Calendar.HOUR_OF_DAY);
		int minutes = now.get(Calendar.MINUTE);
		int seconds = now.get(Calendar.SECOND);
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DAY_OF_MONTH);
		
		DateTime dt = new DateTime(year, month, day, hours, minutes, seconds);
		SkyPos s = new SkyPos(raDegrees/15, decDegrees);
		
		Astro a =new Astro();
		
		a.printDirection(a.RADec2AzEl(l,dt,s, new Direction()));
	}

}

