/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 24/04/2014
 * Time: 12:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Time {
    protected int hour = 9;
    private int minute = 0;
    private String period = "AM";

    public Time(int hour, int minute, String period) {
        this.hour = hour;
        this.minute = minute;
        this.period = period;
        formatTime();
    }

    public void adjust(int minutesToAddToTime) {
        if (minutesToAddToTime >= 60) {
            int hourToAdd = minutesToAddToTime / 60;
            int minutesToAdd = minutesToAddToTime % 60;
            hour = hour + hourToAdd;
            minute = minute + minutesToAdd;
        } else {
            minute = minute + minutesToAddToTime;
        }
        if (minute >= 60) {
            minute -= 60;
            hour += 1;
        }
        if (hour > 11) {
            period = "PM";
        }
        if (hour > 12) {
            hour -= 12;
        }
        formatTime();
    }

    public String formatTime() {
        String hourToString = Integer.toString(hour);
        String minuteToString = Integer.toString(minute);
        if (hourToString.length() == 1) {
            hourToString = ("0" + hourToString);
        }
        if (minuteToString.length() == 1) {
            minuteToString = ("0" + minuteToString);
        }
        String formattedTime = (hourToString + ":" + minuteToString + period);
        return formattedTime;
    }

    public String toString() {
        return formatTime();
    }
}
