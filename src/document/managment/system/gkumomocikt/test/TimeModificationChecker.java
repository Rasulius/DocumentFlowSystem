package document.managment.system.gkumomocikt.test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *@author Rasul
 * Проверяет время редактирования, можно ли в данный момент редактировать 
 * документ. если можно редактировать то получаем true
 */
public class TimeModificationChecker implements DocumentChecker{
    private int start;
    private int end;
    private Date current;
    private Document doc;
    
    public TimeModificationChecker(int startHour, int endHour, Document document, 
            Date currentDate) {
       start = startHour;
       end = endHour;
       current = currentDate;
       doc = document;
    }
    
    private int getHourByCurrentDate(){
        Calendar calendar = GregorianCalendar.getInstance();       
        calendar.setTime(current); 
        return calendar.get(Calendar.HOUR_OF_DAY);
    }    
       
    @Override
    public boolean canBeCreated() {        
        int hour = getHourByCurrentDate();        
        return !(((hour >= start && hour <= 24)|| hour >= 0 && hour <= end)&&
                ((doc.getDocumentStatus()== DocumentStatus.ONSIGN) || 
                (doc.getDocumentStatus() == DocumentStatus.ONMODIFIED)));       
    }
    
    @Override
    public String errorMessage(){
        return "Документ нельзя редактировать c " + start 
                + ":00 до " + end + ":00 часов";
    }
    
}
