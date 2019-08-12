package document.managment.system.gkumomocikt.test;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *  @author Rasul
 *  Реализация класса Простая система документооборота
 */
public class SimpleDocumentFlowSystem implements DocumentFlowSystem{
    // Список документов   
    private LinkedList<Document> documents = new LinkedList<>(); 
    // Текущее время 
    private Date date = new Date(10000000);
    // Начала времени запрета редактирования
    private int redonlyHourStart = 21;
    // Конец времени запрета редактирования
    private int readonlyHourEnd = 7;
    // Максимальное количество открытых документов одной Фирмой
    private int maxCompanyOpenedDocumentCount = 10;   
    // Максимальное рарешенное количество документов в час. 
    private int maxDocumentPerHour = 5;
    // Максимальное количество документооборотов между 2 компаниями
    private int maxWorkflowCompanyCount = 2;
    // Выводить сервисную информацию
    private boolean showServiceInformation = true;        
    // Проверка возможности добавления, модификации документа, есть различные 
    // реализации интерфейса DocumentChecker. которые проверяют время редактирования
    // количество документво в документообороте итд.
    
    
    private boolean checkDocument(Document document){
        DocumentCheckerList checkers = new DocumentCheckerList();      
        
        checkers.add(new TimeModificationChecker(redonlyHourStart, 
                readonlyHourEnd, document, date));
        
        checkers.add(new TotalDocumentCountChecker(documents, 
                document, maxCompanyOpenedDocumentCount));
        
        checkers.add(new DocumentPerHourChecker(documents, 
                document, maxDocumentPerHour));
        
        checkers.add(new CompanyWorkflowCountChecker(maxWorkflowCompanyCount, 
                document, documents));
        
         if (!checkers.checkAllCheckers()){
             System.out.println("!!!!!!!" + checkers.getError());            
             return false;
         }      
        return true;
    }    
      
    // Выводим сервисную информацию о состоянии системы.
    
    private void printServiceInformation(String text, String documentID){
        if (!showServiceInformation)
            return;
        String information = "[Система документооборота]";
        System.out.printf("%s %s [%s] \n", information, text, documentID);      
    }       
    
    public void printWorkflowSettings(){
        System.out.println("-------------------------------------");
        System.out.println("настройки системы");
        System.out.println("-------------------------------------");
        System.out.print("Начала времени запрета редактирования: ");
        System.out.printf("%d:00 \n", redonlyHourStart);
        System.out.print("Конец времени запрета редактирования: ");
        System.out.printf("%d:00 \n", readonlyHourEnd);
        System.out.print("Максимальное количество открытых документов одной Фирмой: ");
        System.out.println(maxCompanyOpenedDocumentCount);
        System.out.print("Максимальное разрешенное количество документов в час: ");
        System.out.println(maxDocumentPerHour);
        System.out.print("Максимальное количество документооборотов между 2 компаниями: ");
        System.out.println(maxWorkflowCompanyCount);   
    }
    
    public void setTime(Date currentDate){
        date = currentDate;
    }
    
    // Установить параметры проверяльщиков. для гибкой настройки.
    public void setCheckerParams(int readonlyHourStartTime, 
          int readonlyHourEndTime, int maxCompanyDocumentflows, int documentsPerHour,
          int maxWorkflowCount){
        redonlyHourStart = readonlyHourEndTime;
        readonlyHourEnd = readonlyHourEndTime;
        maxCompanyOpenedDocumentCount = maxCompanyDocumentflows;
        maxDocumentPerHour = documentsPerHour;   
        maxWorkflowCompanyCount = maxWorkflowCount;
    }

    // Добавляем документ в систему, и подписываем его первой компанией.
      
    @Override
    public void addDocument(Document document) { 
        if (!checkDocument(document)){
            printServiceInformation("ошибка добавления", document.getDocumentID());
            return;
        }
        
        documents.add(document);          
        printServiceInformation("операция: добавление документа", document.getDocumentID()); 
        document.registerObserver(this);
        
        // Пытаемся подписать документ, но сначала проверяем возмождность подписания 
        // документа
        document.setDocumentStatus(DocumentStatus.ONSIGN);
        if (!checkDocument(document)){
            printServiceInformation("ошибка подписи", document.getDocumentID());
            document.setDocumentStatus(DocumentStatus.CREATED);
            return;
        }
        document.getCreatorCompany().signDocument(document);       
    }

    @Override
    public void remove(Document document) {
        printServiceInformation("операция: удаление документа", document.getDocumentID());
        documents.remove(document);
    }

    @Override
    public void signDocument(Document document, Company company) {
        company.signDocument(document);
    }

    /* Событие подписан первой подписью не интересно в данном контексте, оставляем
    * нереализованной, по причине того, что системе интересны ситуации когда 
    * документ полностью подписан.
    */
    @Override
    public void updateSignByCreator(String DocumentID) {
        return;
    }
   
    // Событие получим когда оба подписи будут поставлены в документ
    // по завершения документ получает статус закрыт
    @Override
    public void updateSignByPartner(String DocumentID) {
       printServiceInformation("документы подписаны", DocumentID);
       Document document = findByID(DocumentID);
       document.setDocumentStatus(DocumentStatus.CLOSED);
    }

    @Override
    public Document findByID(String documentID) {
        for (Iterator<Document> it = documents.iterator(); it.hasNext();) {
            Document doc = it.next();
            if(doc.getDocumentID().equals(documentID))
                return doc;
        }        
        return null; 
    }

    @Override
    public void changeDocument(Document document, Company company) {
        
        document.setDocumentStatus(DocumentStatus.ONMODIFIED);        
        if (!checkDocument(document)){
            printServiceInformation("ошибка модификации", document.getDocumentID());
            return;
        }
        
        printServiceInformation("операция: изменение документа", document.getDocumentID());
        if (document.changeDocument(company)){            
            printServiceInformation("смена владельца документа", document.getDocumentID());
            printServiceInformation("владелец", document.getCreatorCompany().getName());
            printServiceInformation("партнер", document.getPartnerCompany().getName());
            document.getCreatorCompany().signDocument(document);        
            document.setDocumentStatus(DocumentStatus.ONSIGN); 
        }            
    }      
}
