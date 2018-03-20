package fernandez.pau.bitacoralist;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by pablofd on 13/03/2018.
 */

public class BitacoraItem {
    private String text;
    private String time;


    public BitacoraItem(String text) {
        this.text = text;
        this.time = getCurrentTime();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public String getCurrentTime() {
        // Obtenir un instant de temps
        Date date = new Date(); // agafa l'instant actual del dispositiu

        // Utilitzem un objecte Calendar per esbrinar els detalls de la data
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min  = calendar.get(Calendar.MINUTE);

        return String.format("%02d/%02d/%04d %02d:%02d", day, month+1, year, hour+1, min);
    }

    public String getTime() { return time;}

    public void setTime(String time) {
        this.time = time;
    }

}
