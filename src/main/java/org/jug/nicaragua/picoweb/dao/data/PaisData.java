package org.jug.nicaragua.picoweb.dao.data;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import org.jug.nicaragua.picoweb.modelo.Pais;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;

public class PaisData {


  public static ListDataProvider<Pais> getData(){
    ListDataProvider<Pais> datos = DataProvider.ofCollection(getPaises());
    
    return datos; 
  }
  
  public static List<Pais> getPaises() {
    // A collection to store our country object
    List<Pais> countries = new ArrayList<Pais>();

    // Get ISO countries, create Country object and store in the collection.
    String[] isoCountries = Locale.getISOCountries();
    for (String country : isoCountries) {
      Locale locale = new Locale("es", country);
      String iso = locale.getISO3Country();
      String code = locale.getCountry();
      String name = locale.getDisplayCountry( Locale.forLanguageTag("es-ES") );

      if (!"".equals(iso) && !"".equals(code) && !"".equals(name)) {
        countries.add(new Pais(iso, code, name));
      }
    }

    // Sort the country by their name and then display the content of countries collection object.
    Collections.sort(countries, new CountryComparator());

    
    return countries;
  }

}


/**
 * CountryComparator class.
 */
class CountryComparator implements Comparator<Pais> {
  private Comparator<Object> comparator;

  CountryComparator() {
    comparator = Collator.getInstance();
  }

  public int compare(Pais c1, Pais c2) {
    return comparator.compare(c1.getName(), c2.getName());
  }
}
