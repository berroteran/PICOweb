package org.jug.nicaragua.picoweb.view.forms;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.jug.nicaragua.picoweb.dao.data.DummyDataGenerator;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class SparklineChart extends VerticalLayout {

  public SparklineChart(final String name, final String unit, final String prefix, final int howManyPoints, final int min, final int max) {
    setSizeUndefined();
    addStyleName("spark");
    setMargin(false);
    setSpacing(false);
    setDefaultComponentAlignment(Alignment.TOP_CENTER);

    int[] values = DummyDataGenerator.randomSparklineValues(howManyPoints, min, max);

    Label current = new Label(prefix + values[values.length - 1] + unit);
    current.setSizeUndefined();
    current.addStyleName(ValoTheme.LABEL_HUGE);
    addComponent(current);

    Label title = new Label(name);
    title.setSizeUndefined();
    title.addStyleName(ValoTheme.LABEL_SMALL);
    title.addStyleName(ValoTheme.LABEL_LIGHT);
    addComponent(title);


    List<Integer> vals = Arrays.asList(ArrayUtils.toObject(values));
    Label highLow = new Label("High <b>" + java.util.Collections.max(vals) + "</b> &nbsp;&nbsp;&nbsp; Low <b>" + java.util.Collections.min(vals) + "</b>", ContentMode.HTML);
    highLow.addStyleName(ValoTheme.LABEL_TINY);
    highLow.addStyleName(ValoTheme.LABEL_LIGHT);
    highLow.setSizeUndefined();
    addComponent(highLow);

  }

  private Component buildSparkline(final int[] values) {

    return null;
  }
}
