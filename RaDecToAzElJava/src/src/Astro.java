package src;

public class Astro {
	private static final double PI = Math.PI;
	public Astro(){
		
	}

	Direction RADec2AzEl(Location l, DateTime dt, SkyPos s, Direction d){

		int year = dt.year;
		int mon = dt.mon;
		int day = dt.day;
		int hour = dt.hour;
		int min = dt.min;
		double sec = dt.sec;

		double lati = l.lati;
		double longi = l.longi;
		double timezone = l.timezone;

		double RA = s.ra;
		double dec = s.dec;

		int utc_day = 0;
		double utc_hourtot = 0.0;
		double hourtot = hour + (min + sec/60.0)/60.0;

		if ((hourtot - timezone)<=0.0)
		{
			utc_hourtot = hourtot-timezone+24.0;
			utc_day = day-1;
		}	
		else if ((hourtot - timezone)>=24.0)
		{
			utc_hourtot = hourtot-timezone-24.0;
			utc_day = day+1;
		}
		else
		{
			utc_hourtot = hourtot-timezone;
			utc_day = day;
		}


		double j2000y = 0;
		double j2000m = 0;
		switch (year)
		{
		case 2000:  j2000y = -1.5; break;
		case 2010:  j2000y = 3651.5; break;
		case 2011:  j2000y = 4016.5; break;
		case 2012:  j2000y = 4381.5; break;
		case 2013:  j2000y = 4747.5; break;
		case 2014:  j2000y = 5112.5; break;
		case 2015:  j2000y = 5477.5; break;
		case 2016:  j2000y = 5842.5; break;
		case 2017:  j2000y = 6208.5; break;
		case 2018:  j2000y = 6573.5; break;
		case 2019:  j2000y = 6938.5; break;
		case 2020:  j2000y = 7303.5; break;

		}
		switch (mon)
		{
		case 1:  j2000m = 0; break;
		case 2:  j2000m = 31; break;
		case 3:  j2000m = 59; break;
		case 4:  j2000m = 90; break;
		case 5:  j2000m = 120; break;
		case 6:  j2000m = 151; break;
		case 7:  j2000m = 181; break;
		case 8:  j2000m = 212; break;
		case 9:  j2000m = 243; break;
		case 10:  j2000m = 273; break;
		case 11:  j2000m = 304; break;
		case 12:  j2000m = 334; break;
		}
		if ((year%4 ==0) && (mon >2))
			j2000m= j2000m+1;

		double d_fromj2000 = j2000y + j2000m + utc_day + utc_hourtot/24.0;
		double GMST = 18.697374558 + 24.06570982441908*d_fromj2000; //Greenwich Mean Sidereal time in hours, not reduced to 0-24 range

		double LanM = (125.04-0.052954*d_fromj2000)*PI/180.0;//Longitude of ascending Node of Moon
		double MLS = (280.47 + 0.98565*d_fromj2000)*PI/180.0; //Mean Longitude of Sun
		double obliq = (23.4393 - 0.0000004*d_fromj2000)*PI/180.0; //obliquity
		double eqeq = 	(-0.000319*Math.sin(LanM)-0.000024*Math.sin(2*MLS))*Math.cos(obliq); //equation of equinoxes

		double GAST = GMST +eqeq; //Greenwich Apparent Sidereal Time
		double LSTfull = (GAST+longi/15.0); //in total hours
		double LST = LSTfull - 24.0*Math.floor(LSTfull/24.0); //in reduced hours

		double HA = (LST-RA)*15.0;//in degrees
		double el = (Math.asin(Math.sin(dec*PI/180.0)*Math.sin(lati*PI/180.0)+Math.cos(dec*PI/180.0)*Math.cos(lati*PI/180.0)*Math.cos(HA*PI/180.0)))*180.0/PI;
		double a = Math.acos((Math.sin(dec*PI/180.0)-Math.sin(el*PI/180.0)*Math.sin(lati*PI/180.0))/(Math.cos(el*PI/180.0)*Math.cos(lati*PI/180.0)))*180.0/PI;
		double az = 0.0;

		if(Math.sin(HA*PI/180.0)<0) 
			az=a;
		else 
			az = 360.0-a;

		d.el=el;
		d.az=az;



		System.out.println("\tHour to T = "+hourtot +"\tHour tot = "+ utc_hourtot +"\tUTC day = "+ utc_day +"\t d from J2000T = "+ d_fromj2000 + "\tGMST = "+GMST + "\teqeq = "+eqeq +"\tHA = "+ HA +"\tElevation = "+  el +"\tAzimuth = "+ az);

		return d;
		//printf("%f \t %f \t %d \t %f \t %f \t %f \t %f \t %f \t %f \n", hourtot, utc_hourtot, utc_day, d_fromj2000, GMST, eqeq, HA, el, az);

	}


	double RADecs2angle(SkyPos s1, SkyPos s2){
		double a1 = (s1.ra)*15.0;
		double d1 = s1.dec;
		double a2 = (s2.ra)*15.0;
		double d2 = s2.dec;
		double dela = a1-a2;
		double ang = Math.acos(Math.sin(d1*PI/180.0)*Math.sin(d2*PI/180.0) + Math.cos(d1*PI/180.0)*Math.cos(d2*PI/180.0)*Math.cos(dela*PI/180))*180.0/PI;
		if(dela > 0)
			return ang;
		else
			return -ang;
	}

	double AzEls2angle(Direction dA, Direction dB){

		double a1 = (dA.az);
		double d1 = dA.el;
		double a2 = (dB.az);
		double d2 = dB.el;
		double dela = a1-a2;
		double ang = Math.acos(Math.sin(d1*PI/180.0)*Math.sin(d2*PI/180.0) + Math.cos(d1*PI/180.0)*Math.cos(d2*PI/180.0)*Math.cos(dela*PI/180))*180.0/PI;
		if(dela > 0)
			return ang;
		else
			return -ang;

	}

	void printDirection (Direction d){

		System.out.println("Azimuth = "+d.az+"\t Elevation = "+d.el);
	}

}
