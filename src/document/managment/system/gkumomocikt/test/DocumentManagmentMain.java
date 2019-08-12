package document.managment.system.gkumomocikt.test;

import java.sql.Time;
import java.util.Date;

/**
 * @author Rasul
 * Главный класс системы - имплементирован от observer.
 * создаются две ситуации: сценарий 1, сценарий 2.
 * updateSignByCreator - вызовется после того, как в системе будут изменены
 * подписи документов, соответственно с ним принимаем решение подписать объект,
 * либо передать документ на модификацию.
 *  
 * Сценарий 1 
 *   1.1. Компания 1 создаёт документ. 
 *   1.2. При добавлении в систему он подписывается и переходит на подписание к компании 2. 
 *   1.3. Компания 2 подписывает документ. 
 *   1.4. Документооборот завершён.  
 * Сценарий 2 
 *  2.1. Компания 1 создаёт документ. 
 *   2.2. При добавлении в систему он подписывается и переходит на подписание к компании 2. 
 *   2.3. Компания 2 вносит изменения. 
 *   2.4. Документ переходит на подписание к компании 1.
 *   2.5. Если компания 1 подписывает документ, то документооборот завершён, 
 *   Если вносит изменения, то пункты 2.3. - 2.4. повторяются, пока не подпишут обе стороны.
 * 
*/
enum Scenario{
     FIRST, 
     CHANGE_PARTNER, 
     CHANGE_FIRST_PARTNER, 
     ENDSTATE
}

public class DocumentManagmentMain implements DocumentStatusObserver{
    
    Scenario testScenario;
    SimpleDocumentFlowSystem documentFlow = new SimpleDocumentFlowSystem();
    
    
    public static void main(String[] args) { 
        new DocumentManagmentMain().initFirstScenario(); // запускаем 1 сценарии                
        new DocumentManagmentMain().initSecondScenario();// запускаем 2 сценарии                
    }
    
    public void initFirstScenario(){
        testScenario = Scenario.FIRST;
        System.out.println("---------------сценарий 001---------------");                               
        SimpleCompany company = new SimpleCompany("Алроса");
        SimpleCompany partner = new SimpleCompany("Газпром");        
        Document document = company.createDocument(partner, new Date(100000));                    
        document.registerObserver(this);
        documentFlow.addDocument(document);    
    }
   
    public void initSecondScenario(){
        testScenario = Scenario.CHANGE_PARTNER;
        System.out.println("---------------сценарий 002---------------");                               
        SimpleCompany company = new SimpleCompany("Роснефть");
        SimpleCompany partner = new SimpleCompany("АФК Система");        
        Document document = company.createDocument(partner, new Time(100000));                    
        document.registerObserver(this);
        documentFlow.addDocument(document); 
    }
   
    @Override
    public void updateSignByCreator(String DocumentID) {
        switch (testScenario){
            case FIRST:{               
                Document document = documentFlow.findByID(DocumentID);
                Company partner = document.getPartnerCompany();
                documentFlow.signDocument(document, partner);
                break;
            }
            case CHANGE_PARTNER:{
                testScenario = Scenario.CHANGE_FIRST_PARTNER;
                Document document = documentFlow.findByID(DocumentID);
                documentFlow.changeDocument(document, document.getPartnerCompany());                                
                break;
            }
                
            case CHANGE_FIRST_PARTNER:{
                testScenario = Scenario.ENDSTATE;
                Document document = documentFlow.findByID(DocumentID);
                documentFlow.signDocument(document, document.getPartnerCompany());
                break;           
            }
                
            case ENDSTATE:
                break;
            default:
                throw new AssertionError(testScenario.name());
        
        }
    }

    @Override
    public void updateSignByPartner(String DocumentID) {       
    }

    
        
            
    
}
