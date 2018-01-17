package uoc.ded.practica;


import java.util.Date;

import uoc.ei.tads.ColaConPrioridad;
import uoc.ei.tads.Diccionario;
import uoc.ei.tads.DiccionarioVectorImpl;
import uoc.ei.tads.Iterador;
import uoc.ei.tads.Recorrido;

public class ContentManagerImpl implements ContentManager {
	
	DiccionarioOrderedVector<String, User> users;
	Diccionario<String, Movie> movies;
	ColaConPrioridad<Movie> ratedMovies;
	
	OrderedVector<Movie> topMovies;
	
	public ContentManagerImpl() {
		this.users = new DiccionarioOrderedVector<String, User> (U, User.CMP);
		this.movies = new DiccionarioVectorImpl<String, Movie>(P);
		this.topMovies=new OrderedVector<Movie>(TOP_MOVIES, Movie.CMP);
		this.ratedMovies = new ColaConPrioridad<Movie>();
	}

	
	@Override
	public void addUser(String idUser, String name, String surname) {
		
		User user = this.users.consultar(idUser);
		if (user == null) {
			this.users.insertar(idUser, new User(idUser, name, surname));
		}
		
	}

	@Override
	public void addMovie(String idMovie, String title, int duration, String director) throws DEDException {
		Movie m = this.movies.consultar(idMovie);
		if (m!=null) throw new DEDException(Messages.MOVIES_ALREADY_EXIST);
		
		m = new Movie(idMovie, title, duration, director);
		
		this.movies.insertar(idMovie, m);
	}

	@Override
	public void watchMovie(String idUser, String idMovie)  {

		User u = this.users.consultar(idUser);
		// @pre
		if (u==null){try {
			throw new DEDException(Messages.USER_NOT_FOUND);
		} catch (DEDException e) {
			e.printStackTrace();
		}}
		
		Movie m = this.movies.consultar(idMovie);
		
		// @pre 
		if (m==null)
			try {
				throw new DEDException (Messages.MOVIE_NOT_FOUND);
			} catch (DEDException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		u.setWatchingMovie(m);
		
	}

	@Override
	public void endMovie(String idUser, Date dateTime) throws DEDException {
		User u = this.getUser(idUser);
		if (u.isWatchingMovie()) {
			Movie m = u.watchingMovie();
			m.incViews();
			WatchedMovie wm = new WatchedMovie(m, dateTime);
			u.addWatchedMovie(wm);
			u.endMovie();
			this.topMovies.update(m);
		}
		else throw new DEDException(Messages.USER_WATCHING_NO_MOVIES);
	}

	@Override
	public void pauseMovie(String idUser, int minute) throws DEDException {
		PausedMovie pm = null;
		User u = this.getUser(idUser);
		if (u.isWatchingMovie()) {
			pm = u.pauseMovie(minute);	
		}
		else throw new DEDException(Messages.USER_WATCHING_NO_MOVIES);
		
	}

	/*@Override
	public void resumeMovie(String idUser) throws DEDException {
		User u = this.getUser(idUser);
		PausedMovie pm = u.pausedMovie();
		if (pm==null) throw new DEDException(Messages.NO_PAUSED_MOVIE);
		
		u.setWatchingMovie(pm.getMovie());	
		u.resumeMovie();
	}*/

	
	@Override
	public Iterador<WatchedMovie> getUserWatchedMovies(String idUser) throws DEDException {
		User u = this.users.consultar(idUser);
		
		// @pre
		if (u == null)
		{
			throw new DEDException(Messages.USER_NOT_FOUND);
		}
		else
		{
			ListaEncadenadaOrdenada<WatchedMovie> ll = u.getWatchedMovies();	
			if (ll.estaVacio()) throw new DEDException(Messages.NO_WATCHED_MOVIES);
			else
			{
				return (ll.elementos());
			}
		}
				
	}


	@Override
	public Iterador<Movie> topMovies() throws DEDException {
		if (this.topMovies.estaVacio()) throw new DEDException(Messages.NO_MOVIES);
		return this.topMovies.elementos();
	}
	
	public User getUser(String idUser) throws DEDException {
		User u = this.users.consultar(idUser);
		if (u==null) throw new DEDException(Messages.USER_NOT_FOUND);
		return u;
	}


	@Override
	public Iterador<User> users() throws DEDException {
		if (this.users.estaVacio()) throw new DEDException(Messages.NO_USERS);
		return this.users.elementos();
	}


	@Override
	public Iterador<Movie> movies() throws DEDException {
		if (this.movies.estaVacio()) throw new DEDException(Messages.NO_MOVIES);
		return this.movies.elementos();
	}


	@Override
	public void resumeMovie(String idUser, String idMovie) throws DEDException {
		User u = getUser(idUser);
		if(u!=null)
		{
			if(u.pausedMovies()!=null && !u.pausedMovies().estaVacio() )
			{
				if(!u.resumeMovie(idMovie))throw new DEDException(Messages.NO_PAUSED_MOVIE);
			}
			else
			{
				throw new DEDException(Messages.USER_WATCHING_NO_MOVIES);
			}
		}
		else
		{
			throw new DEDException(Messages.USER_NOT_FOUND);
		}
	}


	@Override
	public WatchedMovie getWatchedMovie(String idUser, Date date)throws DEDException {
		User u = getUser(idUser);
		WatchedMovie watched = null;
		if(u.getWatchedMovies()!=null && !u.getWatchedMovies().estaVacio())
		{
			ListaEncadenadaOrdenada<WatchedMovie> ll = u.getWatchedMovies();
			Recorrido<WatchedMovie> recorrido = ll.posiciones();
			while(recorrido.haySiguiente())
			{
				watched = recorrido.siguiente().getElem();
				
				if(watched.getDate().compareTo(date)==0)
				{
					break;
				}
				
				else
				{
					watched = null;
				}
			}
			
			if(watched==null)
			{
				throw new DEDException(Messages.NO_WATCHED_MOVIES);
			}
			
			
		}
		else
		{
			throw new DEDException(Messages.NO_WATCHED_MOVIES);
		}
		return watched;
	}


	@Override
	public void rateMovie(String idMovie, int rating) {
		try {
			Movie m = getMovie(idMovie);
			if(!ratedMovies.estaVacio())
			{
				int lastRates=0,ratesCounter=1;
				Recorrido<Movie> recorrido = ratedMovies.posiciones();
				while(recorrido.haySiguiente())
				{
					Movie ratedMovie = recorrido.siguiente().getElem();
					if(ratedMovie.getIdMovie().equals(idMovie))
					{
						lastRates+=ratedMovie.getRating();
						ratesCounter++;
					}
				}
				
			
				setRate(m, (lastRates+rating)/ratesCounter);
				
			}
			else
			{
				setRate(m,rating);
			}
			ratedMovies.encolar(m);
		} catch (DEDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setRate(Movie m, int rate)
	{
		m.setRating(rate);
	}


	@Override
	public Movie bestRated() throws DEDException {
		if (this.ratedMovies.estaVacio()) throw new DEDException(Messages.NO_MOVIES);
		return this.ratedMovies.desencolar();
	}


	@Override
	public Movie getMovie(String idMovie) throws DEDException {
		Movie m = this.movies.consultar(idMovie);
		if (m==null) throw new DEDException(Messages.MOVIE_NOT_FOUND);
		return m;
	}




    
}
