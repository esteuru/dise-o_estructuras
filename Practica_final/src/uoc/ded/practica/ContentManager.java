package uoc.ded.practica;

import java.util.Date;

import uoc.ei.tads.Iterador;

/**
 * Definición del TAD de gestión de contenidos multimedia
 */
public interface ContentManager {

	public static final int U = 500;
	public static final int P = 100;
	public static final int TOP_MOVIES = 10;

	/**
	 * Método que añade un usuario en el sistema
	 * 
	 * @post: Si el código de usuario es nuevo, los usuarios serán los mismos 
	 *        más un nuevo usuario con los datos indicados. Sino, los datos del 
	 *        usuario se habrán actualizado con los nuevos.
	 * 
	 * @param idUser
	 *            identificador deL usuario
	 * @param name
	 *            nombre del usuario
	 * @param surname
	 *            apellidos del usuario
	 */
	public void addUser(String idUser, String name, String surname);

	/**
	 * Método que añade una película en el sistema
	 * 
	 * @pre cIerto
	 * @post: Las películas serán las mismas más una nueva con los datos 
	 *        indicados. Si existe una película con id idMovie, devuelve error.
	 * 
	 * @param idMovie
	 *            identificador de la película
	 * @param title
	 *            título de una película
	 * @param duration
	 *            tiempo que dura una película
	 * @param director
	 *            director de la película
	 * @throws DEDException
	 *             lanza una excepción en caso de error préviamente indicado
	 */
	public void addMovie(String idMovie, String title, int duration, String director) throws DEDException;

	/**
	 * Método que indica la visualización de una película por parte de un usuario
	 * 
	 * @pre: la película y el usuario deben existir.
	 * @post la película que está visualizando el usuario pasa a ser la 
	 *       película especificada.
	 * 
	 * @param idUser
	 *            identificador deL usuario
	 * @param idMovie
	 *            identificador de la película
	 */
	public void watchMovie(String idUser, String idMovie);

	/**
	 * Método que indica al sistema la finalización de una película 
	 * por parte de un usuario
	 * 
	 * @pre cierto.
	 * @post Las películas vistas por el usuario serán las mismas más una nueva 
	 *       entrada con la película especificada y la fecha y hora de 
	 *       finalización también especificadas. Si la película no está siendo 
	 *       visualizada por el usuario devuelve un error. El usuario no estará 
	 *       visualizando ninguna película.
	 * 
	 * @param idUser
	 *            identificador del usuario
	 * @param dateTime
	 *            fecha en la que se produce la finalización de la película
	 * @throws DEDException
	 *             lanza una excepción en caso de error préviamente indicado
	 */
	public void endMovie(String idUser, Date dateTime) throws DEDException;

	/**
	 * Método que indica al sistema la pausa de una película por parte de un 
	 * usuario
	 * 
	 * @pre cierto
	 * @post la película en pausa por el usuario pasa a ser la película 
	 *       especificada en el minuto especificado. El usuario pasa a no 
	 *       estar visualizando ninguna película. Si el usuario no existe, 
	 *       no está visualizando ninguna película o el minuto es mayor que 
	 *       la duración de la película, devuelve un error.
	 *
	 * @param idUser
	 *            identificador del usuario que realiza una pausa de la
	 *            película que se está visualizando
	 *
	 * @param minute
	 *            minuto en el que se realiza la pausa
	 * @throws DEDException
	 *             lanza una excepción en caso de error préviamente indicado
	 */
	public void pauseMovie(String idUser, int minute) throws DEDException;

	/**
	 * Método que indica al sistema que se vuelve a visualizar
	 * la película que previamente se ha pausado
	 * 
	 * @pre cierto
	 * @post la película que está visualizando el usuario pasa a ser la 
	 *       película que tiene en pausa. El usuario pasa a no tener 
	 *       ninguna película en pausa. Si el usuario no existe o no 
	 *       tiene ninguna película idMovie en pausa devuelve un error.
	 *
	 * @param idUser
	 *            identificador del usuario
	 * @param idMovie
	 * 			  identificador de la película 
	 * @throws DEDException
	 *             lanza una excepción en caso de error préviamente indicado
	 */
	public void resumeMovie(String idUser, String idMovie) throws DEDException;

	/**
	 * Método que proporciona las visualizaciones de un usuario por orden
	 * cronològico
	 * 
	 * @pre existe un usuario con identificador idUser
	 * @post devuelve un iterador para recorrer las visualizaciones del 
	 * 		 usuario por orden cronológico.
	 * 
	 * @param idUser
	 *            identificador del usuario
	 * @return retorna un iterador con las visualizaciones del usuario
	 * @throws DEDException
	 *             lanza una excepción en caso de error préviamente indicado
	 */
	public Iterador<WatchedMovie> getUserWatchedMovies(String idUser) throws DEDException;

	/**
	 * Método que retorna un iterador de las 10 películas más vistas. En caso que
	 * no haya ninguna película se mostrará un mensaje
	 * 
	 * @pre cert
	 * @post devuelve un iterador para recorrer las 10 películas más vistas
	 *       ordenadas según el número total de visualizaciones.
	 *
	 * @return retorna un iterador con las 10 películas
	 * @throws DEDException
	 *             lanza una excepción en caso de error préviamente indicado
	 */
	public Iterador<Movie> topMovies() throws DEDException;

	
	/**
	 * 
	 * @pre el usuario tiene que existir 
	 * @post retorna la visualitzación realizada por un usuario en una determinada   
	 *       fecha (dia y hora). Si no hay ninguna visualización en la fecha retorna
	 *       un error.
	 * @param idUser
	 * @param date
	 * @return retorna la visualitzación realizada por un usuario en una determinada   
	 *       fecha (dia y hora). Si no hay ninguna visualización en la fecha retorna
	 *       un error.
	 */
	public WatchedMovie getWatchedMovie(String idUser, Date date) throws DEDException ;
	
	/**
	 * @pre   la película debe existir y la puntuación debe ser un entero 
	 *        entre 0 y 100
	 * @post  Las películas puntuadas son las mismas más una nueva con la puntuación indicada
	 * @param idMovie identificador de la película
	 * @param rating puntuación
	 */
	public void rateMovie(String idMovie, int rating);
	
	
	/**
	 * @pre cierto 
	 * @post las películas puntuadas son las mismas menos la película con más puntuación    
	 * 		  Si no hay ninguna película retorna un error
	 * @return retorna la película con más puntuación
	 */
	public Movie bestRated() throws DEDException  ;
	
	/**
	 * Método auxiliar para mostrar información sobre un usuario
	 * 
	 * @param idUser
	 *            identificador del usuario
	 * @return retorna el usuario especificado
	 * @throws DEDException
	 *             lanza una excepción en caso de error préviamente indicado
	 */
	public User getUser(String idUser) throws DEDException;

	/**
	 * Método auxiliar que proporciona los usuarios en el sistema. En caso
	 * que no haya usuarios se mostrará un error 
	 * 
	 * @return retorna un iterador con los usuarios en el sistema
	 * @throws DEDException
	 *             lanza una excepción en caso de error préviamente indicado
	 */
	public Iterador<User> users() throws DEDException;

	/**
	 * Método auxiliar que proporciona las películas del sistema. En caso 
	 * que no haya películas se mostrará un error
	 * 
	 * @return retorna un iterador con las películas
	 * @throws DEDException
	 *             lanza una excepción en caso de error préviamente indicado
	 */
	public Iterador<Movie> movies() throws DEDException;
	
	/**
	 * Método auxiliar que proporciona información sobre una película. En caso que
	 * no exista la película se mostrará un error 
	 * 
	 * @param idMovie
	 *            identificador de la película
	 * 
	 * @return retorna una película identificada por idMovie
	 * @throws DEDException
	 *             Lanza una excepción en caso que no exista la película 
	 */
	public Movie getMovie(String idMovie) throws DEDException;

}
