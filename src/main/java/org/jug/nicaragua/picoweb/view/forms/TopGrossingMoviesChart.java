package org.jug.nicaragua.picoweb.view.forms;

import java.util.ArrayList;
import java.util.List;
import org.jug.nicaragua.picoweb.PICOWEBUI;
import org.jug.nicaragua.picoweb.domain.Movie;

@SuppressWarnings("serial")
public class TopGrossingMoviesChart {

  public TopGrossingMoviesChart() {

    List<Movie> movies = new ArrayList<Movie>(PICOWEBUI.getDataProvider().getMovies());
  }


}
