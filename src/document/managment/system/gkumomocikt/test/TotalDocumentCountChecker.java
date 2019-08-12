package document.managment.system.gkumomocikt.test;

import java.util.LinkedList;

/**
 *@author Rasul
 * Проверка того что, фирма учавствует больше чем в 10 документах
 */
public class TotalDocumentCountChecker implements DocumentChecker {
    private Integer maxDocumentCount;
    private Document document;
    private LinkedList<Document> documentList = new LinkedList<>();
    
    public TotalDocumentCountChecker(LinkedList<Document> documents, 
            Document newDocument, int maxOpenDocumentCount){
        maxDocumentCount = maxOpenDocumentCount;
        documentList = documents; 
        document = newDocument;
    }
    
    // Подсчет количество документооборотов, если более maxDocumentCount
    // то вернем ошибка.    
    @Override
    public boolean canBeCreated() {
        long count = 0;
        for (Document iter: documentList){
           String company = iter.getCreatorCompany().getName();
           String partnerCompany = iter.getPartnerCompany().getName();
           String documentCompany = document.getCreatorCompany().getName();
            
            if ((company.equalsIgnoreCase(documentCompany)
                    ||partnerCompany.equalsIgnoreCase(documentCompany))
                    &&(iter.getDocumentStatus() != DocumentStatus.CLOSED))
                    count++;
        }      
        return  count < maxDocumentCount;       
    }

    @Override
    public String errorMessage() {
        return "Нельзя участвовать больше чем  " + maxDocumentCount.toString() 
                + " документооборотов";        
    } 
    
}
