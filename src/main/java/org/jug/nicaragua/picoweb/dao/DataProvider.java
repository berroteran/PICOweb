package org.jug.nicaragua.picoweb.dao;

import java.util.Collection;
import java.util.Date;
import org.jug.nicaragua.picoweb.domain.DashboardNotification;
import org.jug.nicaragua.picoweb.domain.Movie;
import org.jug.nicaragua.picoweb.domain.MovieRevenue;
import org.jug.nicaragua.picoweb.domain.Transaction;
import org.jug.nicaragua.picoweb.modelo.Rol;
import org.jug.nicaragua.picoweb.modelo.Usuario;

/**
 * Backend API.
 */
public interface DataProvider {
  /**
   * @param count Number of transactions to fetch.
   * @return A Collection of most recent transactions.
   */
  Collection<Transaction> getRecentTransactions(int count);

  /**
   * @param id Movie identifier.
   * @return A Collection of daily revenues for the movie.
   */
  Collection<MovieRevenue> getDailyRevenuesByMovie(long id);

  /**
   * @return Total revenues for each listed movie.
   */
  Collection<MovieRevenue> getTotalMovieRevenues();

  /**
   * @param userName
   * @param password
   * @return Authenticated used.
   * @throws Exception 
   */
  Usuario authenticate(String userName, String password) throws Exception;

  /**
   * @return The number of unread notifications for the current user.
   */
  int getUnreadNotificationsCount();

  /**
   * @return Notifications for the current user.
   */
  Collection<DashboardNotification> getNotifications();

  /**
   * @return The total summed up revenue of sold movie tickets
   */
  double getTotalSum();

  /**
   * @return A Collection of movies.
   */
  Collection<Movie> getMovies();

  /**
   * @param movieId Movie's identifier
   * @return A Movie instance for the given id.
   */
  Movie getMovie(long movieId);

  /**
   * @param startDate
   * @param endDate
   * @return A Collection of Transactions between the given start and end dates.
   */
  Collection<Transaction> getTransactionsBetween(Date startDate, Date endDate);


  /**
   * metodos creados por mi
   * 
   */



  Collection<Usuario> getAllUsuarios();


  
  Usuario findUserByLogin(String login);

  Collection<Rol>  getRolAll();

}


