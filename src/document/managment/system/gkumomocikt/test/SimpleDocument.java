package document.managment.system.gkumomocikt.test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
/**
 * @author Rasul
 */
public class SimpleDocument implements Document {
    
    private Company creatorCompany;
    private Company partnerCompany;
    private String documentID;
    private Date documentDate;
    private DocumentSignature creatorSignature;
    private DocumentSignature partnerSignature;
    private List<DocumentStatusObserver> observers; // Список наблюдателей.
    private DocumentStatus documentStatus;
    
    /* Конструктор, параметры компания создатель, партнер, дата создания.*/
    
    public SimpleDocument(Company creator, Company parther, Date creationDate){
        creatorCompany = creator;
        partnerCompany = parther;
        documentDate = creationDate;            
        observers = new LinkedList<>();
        creatorSignature = new SimpleDocumentSignature();
        partnerSignature = new SimpleDocumentSignature();
        documentID = GeneratorID.generateID();        
        partnerCompany.registerPartnerDocument(this);
        setDocumentStatus(DocumentStatus.CREATED);
        printServiceInformation(creator, "создал документ");               
    }      
    
    private void printServiceInformation(Company company, String text){
        System.out.printf("[%s] %s [%s] \n", company.getName(),
                text, documentID);    
    }
    
    @Override
    public Company getCreatorCompany() {
        return creatorCompany; 
    }

    @Override
    public Company getPartnerCompany() {
        return partnerCompany; 
    }

      @Override
    public Date getDocumentDate() {
        return documentDate;
    }
    // регистрируем наблюдателя.
    @Override
    public void registerObserver(DocumentStatusObserver observer) {
        observers.add(observer);
    }
    // Удалаяем наблюдателя
    @Override
    public void removeObserver(DocumentStatusObserver observer) {
        observers.remove(observer);
    }
    // Оповещаем наблюдатетелей 
    @Override
    public void notifyObservers(DocumentStateEvent event, String documentID) {
        switch (event){
            case UPDATE_FIRST_SIGN:
                observers.stream()
                         .forEachOrdered((observer)-> {observer.updateSignByCreator(documentID);});
                break;
            case UPDATE_SECOND_SIGN:
                observers.stream()
                         .forEach((observer)->{observer.updateSignByPartner(documentID);});
                break;
            case DOCUMENT_SIGNED:
                break;
            default:
                throw new AssertionError(event.name());
        }         
    }
   
    @Override
    public String getDocumentID() {
        return documentID;
    }

    @Override
    public DocumentSignature getCreatorSignature() {
        return  creatorSignature;
    }

    @Override
    public DocumentSignature getPartnerSignature() {
        return  partnerSignature;
    }

    @Override
    public void setSignature(DocumentSignature signature) {
        // устанавливаем первую подпись
        if (signature.getOwnerName().equals(creatorCompany.getName())){            
            printServiceInformation(creatorCompany, "подписал документ");            
            notifyObservers(DocumentStateEvent.UPDATE_FIRST_SIGN, documentID);            
        }       
        else if (partnerSignature.hasSignature()){
           printServiceInformation(partnerCompany, "документ отправлен");
           notifyObservers(DocumentStateEvent.UPDATE_SECOND_SIGN, documentID);            
        }
        else{                      
           printServiceInformation(partnerCompany, "документ подписан ");
           notifyObservers(DocumentStateEvent.UPDATE_SECOND_SIGN, documentID);       
        }     
        
    }

    @Override
    public boolean changeDocument(Company company) {
        byte signature[] = null;
        
        if (creatorCompany.equals(company)){
            creatorSignature.setSignature(signature);
            return  true;
        }         
        else if (partnerCompany.equals(company)){
            creatorSignature.clearSign();
            SimpleCompany tempCompany = new SimpleCompany((SimpleCompany)creatorCompany); 
            creatorCompany = new SimpleCompany((SimpleCompany)partnerCompany); 
            partnerCompany = new SimpleCompany((SimpleCompany)tempCompany); 
            
            return true;
        }
        else {
            return false;
        }   
        
    }

    @Override
    public DocumentStatus getDocumentStatus() {
        return documentStatus;
    }

    @Override
    public final void setDocumentStatus(DocumentStatus status) {
        documentStatus = status;
    }
    
}
