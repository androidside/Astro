package src;


public class main {



	public static void main( String[] args ) {
		//Just for Testing
		Location l = new Location(34.4717,104.2455,-6); //Fort Sumner
		DateTime dt = new DateTime(2016, 9, 21, 3, 14, 00);
		SkyPos s = new SkyPos(40.75, 11.14);
		
		Astro a =new Astro();
		
		a.printDirection(a.RADec2AzEl(l,dt,s, new Direction()));
	}

}