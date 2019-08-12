/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document.managment.system.gkumomocikt.test;

import static java.lang.Math.abs;
import java.util.Date;
import java.util.LinkedList;

/**
 * @author Rasul
 * 
 * Проверяющий класс который проверяет, возможности создания более 5 документов
 * в час одной компанией.
 * 
 */
public class DocumentPerHourChecker implements DocumentChecker{
    
    private Integer maxDocumentsPerHour; 
    private LinkedList<Document> documentList;
    private Document document;
    
    DocumentPerHourChecker(LinkedList<Document> documents, Document newDocument,
            int docCountPerHour){
        maxDocumentsPerHour = docCountPerHour;
        documentList = documents;        
        document = newDocument;
    }

    private int minutesDiff(Date earlierDate, Date laterDate)
    {
        if( earlierDate == null || laterDate == null ) 
            return 0;
        return abs((int)((laterDate.getTime()/60000) - (earlierDate.getTime()/60000)));
    }
    
    @Override
    public boolean canBeCreated() {
        LinkedList <Date> dates = new LinkedList<>();
        int count = 0;
        
        for(Document iter: documentList){
            String company = iter.getCreatorCompany().getName();           
            String documentCompany = document.getCreatorCompany().getName();
            
            if (company.equalsIgnoreCase(documentCompany)){
                count++;
                dates.add(iter.getDocumentDate());
            }
        }            
        if (count==0)
           return true;
        
        dates.sort((Date x,Date x1)->Integer.max((int)x.getTime(),
            (int)x1.getTime()));
        
        
        int diff = minutesDiff(dates.getFirst(), dates.getLast());           
         
        return !((diff <= 60) && (count > maxDocumentsPerHour));  
    }

    @Override
    public String errorMessage() {
        return "В компании нельзя создавать больше " +  maxDocumentsPerHour.toString() 
                + "документов в час";
    }
    
    
    
    
        
  
}
