package phsc.countdown;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.temporal.ChronoUnit;

class Countdown{
    private int diffInDays;
    private int diffInHours;
    private int diffInMinutes;
    private int diffInSeconds;
    private int diffInNanoSeconds;
    private LocalDateTime now;
    private int thenMonth;
    private int thenDay;
    private int thenYear;
    private int thenHour;
    private int thenMinute;
    private int thenSecond;

    void countdown(String date,String time,boolean dst){
        String[] dateArray=date.split("/");
        //TODO: Split "." and "-" (Test what works when this gets split)
        String[] timeArray=time.split(":");

        now=LocalDateTime.now(ZoneId.of("EST"));

        thenMonth=now.getMonthValue();
        try{
            if(Integer.parseInt(dateArray[0])<=12&&Integer.parseInt(dateArray[0])>0)thenMonth=Integer.parseInt(dateArray[0]);
        } catch(ArrayIndexOutOfBoundsException|NumberFormatException e){
            e.printStackTrace();
        }

        thenDay=now.getDayOfMonth();
        try{
            if(Integer.parseInt(dateArray[1])<=31&&Integer.parseInt(dateArray[1])>0)thenDay=Integer.parseInt(dateArray[1]);
        } catch(ArrayIndexOutOfBoundsException|NumberFormatException e){
            e.printStackTrace();
        }

        thenYear=now.getYear();
        try{
            if(Integer.parseInt(dateArray[2])>0)thenYear=Integer.parseInt(dateArray[2]);
        } catch(ArrayIndexOutOfBoundsException|NumberFormatException e){
            e.printStackTrace();
        }

        thenHour=0;
        try{
            if(Integer.parseInt(timeArray[0])<=24&&Integer.parseInt(timeArray[0])>0)thenHour=Integer.parseInt(timeArray[0]);
        } catch(ArrayIndexOutOfBoundsException|NumberFormatException e){
            e.printStackTrace();
        }

        thenMinute=0;
        try{
            if(Integer.parseInt(timeArray[1])<=60&&Integer.parseInt(timeArray[1])>0)thenMinute=Integer.parseInt(timeArray[1]);
        } catch(ArrayIndexOutOfBoundsException|NumberFormatException e){
            e.printStackTrace();
        }

        thenSecond=0;
        try{
            if(Integer.parseInt(timeArray[2])<=60&&Integer.parseInt(timeArray[2])>0)thenSecond=Integer.parseInt(timeArray[2]);
        } catch(ArrayIndexOutOfBoundsException|NumberFormatException e){
            e.printStackTrace();
        }

        LocalDateTime then=LocalDateTime.of(thenYear,thenMonth,thenDay,thenHour,thenMinute,thenSecond,0);

        if(dst)then=then.plusHours(1);

        double doubleDays=now.until(then,ChronoUnit.DAYS);
        if(doubleDays<1)diffInDays=0;
        else diffInDays=(int)doubleDays;
        double doubleHours=now.until(then,ChronoUnit.HOURS);
        if(doubleHours<1)diffInHours=0;
        else diffInHours=(int)doubleHours-(int)(doubleDays*24);
        double doubleMinutes=now.until(then,ChronoUnit.MINUTES);
        if(doubleMinutes<1)diffInMinutes=0;
        else diffInMinutes=(int)doubleMinutes-(int)(doubleHours*60);
        double doubleSeconds=now.until(then,ChronoUnit.SECONDS);
        if(doubleSeconds<0)diffInSeconds=0;
        else diffInSeconds=(int)doubleSeconds-(int)(doubleMinutes*60);
        diffInNanoSeconds=(int)(now.until(then,ChronoUnit.NANOS)-(int) doubleSeconds*1000000000)/1000000;
    }

    int getDays(){
        return diffInDays;
    }
    int getHours(){
        return diffInHours;
    }
    int getMinutes(){
        return diffInMinutes;
    }
    int getSeconds(){
        return diffInSeconds;
    }
    int getNanoSeconds(){
        return diffInNanoSeconds;
    }

    //Testing getters
    int getNowSecond(){
        return now.getSecond();
    }
    int getNowDayOfMonth(){
        return now.getDayOfMonth();
    }
    int getNowMonthValue(){
        return now.getMonthValue();
    }
    int getNowYear(){
        return now.getYear();
    }
    int getNowHour(){
        return now.getHour();
    }
    int getNowMinute(){
        return now.getMinute();
    }
    int getThenMonth(){
        return thenMonth;
    }
    int getThenDayOfMonth(){
        return thenDay;
    }
    int getThenYear(){
        return thenYear;
    }
    int getThenHour(){
        return thenHour;
    }
    int getThenMinute(){
        return thenMinute;
    }
    int getThenSecond(){
        return thenSecond;
    }
}