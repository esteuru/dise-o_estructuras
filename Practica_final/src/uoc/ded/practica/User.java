package uoc.ded.practica;

import java.util.Comparator;

import uoc.ei.tads.ListaEncadenada;
import uoc.ei.tads.Posicion;
import uoc.ei.tads.Recorrido;

/**
 * Clase que modela una entidad de información usuario
 *
 */
public class User {
	
	private String idUser;
	private String name;
	private String surname;
	
	private ListaEncadenada<PausedMovie> pausedMovies;
	
	private Movie watchingMovie;
	private ListaEncadenadaOrdenada<WatchedMovie> watchedMovies;
	
	public static Comparator<String> CMP = new Comparator<String>() {

		@Override
		public int compare(String idUser1, String idUser2) {
			int ret = idUser1.compareTo(idUser2);
			return ret;
		}
	};
	
	public User(String idUser, String name, String surname) {
		this.idUser=idUser;
		this.name = name;
		this.surname = surname;
		this.watchingMovie=null;
		this.pausedMovies= new ListaEncadenada<PausedMovie>();
		this.watchedMovies = new ListaEncadenadaOrdenada<WatchedMovie>(WatchedMovie.CMP);
	}

	public String getIdUser() {
		return this.idUser;
	}

	public boolean isWatchingMovie() {
		return (this.watchingMovie!=null);
	}

	public Movie watchingMovie() {
		return this.watchingMovie;
	}

	public void addWatchedMovie(WatchedMovie wm) {
		this.watchedMovies.add(wm);
	}

	public PausedMovie pauseMovie(int minute) throws DEDException {
		PausedMovie pm = null;
		Movie m = this.watchingMovie();
		if (minute > m.getDuration()) throw new DEDException(Messages.MOVIE_DURATION_EXCEEDED);
		else {		
			pm = new PausedMovie(m, minute);
			this.pausedMovies.insertarAlFinal(pm);
			this.watchingMovie = null;
		}
		return pm;
	}

	public ListaEncadenada<PausedMovie> pausedMovies() {
		return this.pausedMovies;
	}

	public void setWatchingMovie(Movie movie) {
		this.watchingMovie=movie;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer("id: ").append(this.idUser).append(" ");
		sb.append("name: ").append(this.name).append(" ");
		sb.append("surname: ").append(this.surname).append(" ");
		if (this.isWatchingMovie()) sb.append(Messages.LS+"watching movie: "+Messages.LS).append(this.watchingMovie.toString("\t")).append(Messages.LS);
		if (!this.pausedMovies.estaVacio())
		{
            Recorrido<PausedMovie> recorrido = pausedMovies.posiciones();
            sb.append(Messages.LS+"paused movies: ");
			while(recorrido.haySiguiente())
			{
				sb.append(Messages.LS).append(recorrido.siguiente().getElem().toString("\t"));
			}
			sb.append(Messages.LS);
		}
		if(!watchedMovies.estaVacio())
		{
			Recorrido<WatchedMovie> recorrido = watchedMovies.posiciones();
            sb.append(Messages.LS+"watched movies: ");
			while(recorrido.haySiguiente())
			{
				sb.append(Messages.LS).append("\t"+recorrido.siguiente().getElem().toString());
			}
			sb.append(Messages.LS);
		}
		
		return sb.toString();
	}

	public boolean resumeMovie(String idMovie) {
		boolean resumed = false;
			Recorrido<PausedMovie> recorridoPaused = this.pausedMovies.posiciones();
			
			while(recorridoPaused.haySiguiente() && !resumed)
			{
				Posicion<PausedMovie> paused = recorridoPaused.siguiente();
				PausedMovie pausedMovie = paused.getElem();
				if(pausedMovie.getMovie().getIdMovie().equals(idMovie))
				{
					this.pausedMovies.borrar(paused);
					this.watchingMovie = pausedMovie.getMovie();
					resumed=true;
				}
			}
		
		return resumed;
	}

	public void endMovie() {
		this.watchingMovie=null;
	}

	public ListaEncadenadaOrdenada<WatchedMovie> getWatchedMovies() {
		return this.watchedMovies;		
	}

}
