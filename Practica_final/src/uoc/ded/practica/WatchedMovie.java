package uoc.ded.practica;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Clase que modela una película que se está visualizando
 * Se guarda la referencia de la película y la fecha 
 *
 */
public class WatchedMovie {
	public static final Comparator<WatchedMovie> CMP = new Comparator<WatchedMovie>() {

		@Override
		public int compare(WatchedMovie wm1, WatchedMovie wm2) {
			return wm2.date.compareTo(wm1.date);
		}
	};
	
	private Movie m;
	private Date date;

	public WatchedMovie(Movie currentMovie, Date dateTime) {
		this.m=currentMovie;
		this.date=dateTime;
	}
	
	public Movie getMovie() {
		return this.m;
	}
	
	public Date getDate()
	{
		return this.date;
	}
	
	public String toString() {

		SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dateString = dt1.format(date);
		StringBuffer sb = new StringBuffer(this.m.getIdMovie()+" ").append(m.getTitle()).append(" ").append(dateString);
		
		return sb.toString();
	}

}
