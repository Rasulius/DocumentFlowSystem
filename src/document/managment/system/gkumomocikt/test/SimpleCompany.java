package document.managment.system.gkumomocikt.test;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Rasul 
 * 
 */
public class SimpleCompany implements Company{
    
    private String companyName;
    public LinkedList<Document> documents; 
               
    public SimpleCompany(String name){
        companyName = name;
        documents = new LinkedList<>();
    }        
    
    public SimpleCompany(SimpleCompany source){
        companyName = source.getName();
        documents = (LinkedList<Document>)source.documents.clone();
    }
    
// Поиск документа по уникальному номеру 
    private Document findByID(String documentID){
        Document document = documents.stream()
                .filter((docum)->docum.getDocumentID().equals(documentID))
                .findAny()
                .get();
        return document;
    }
    
    public  void initDocumentList(LinkedList<Document> flowDocumentList){    
        documents = flowDocumentList;
    }
    
    @Override
    public String getName() {
        return companyName;
    }
    /* 
     Вызываем фабрику. 
     Создаем простой документ.
     регистрируем наблюдателей(парнер и система документооборота)
     Добавляем документ в систему документооборота     
    */
    @Override
    public Document createDocument(Company parther, Date creationDate) {
        DocumentFactory documentFactory = new DocumentFactory();
        SimpleDocument document = documentFactory.createSimpleDocument(this, 
                parther, creationDate);                
        document.registerObserver(parther);          
        documents.add(document);           
        return document;        
    }
       
    @Override
    public void signDocument(Document document) {
        DocumentSignature signature = generateSignature();
        document.setSignature(signature);      
    }
    
    @Override
    public void registerPartnerDocument(Document document) {
        documents.add(document);
    }

    @Override
    public void updateSignByCreator(String documentID) {
        System.out.println("[передача документа] [" + documentID + "]");               
    }

    @Override
    public void updateSignByPartner(String documentID) {
                
    }

    @Override
    public DocumentSignature generateSignature() {
        
        SimpleDocumentSignature signature = new SimpleDocumentSignature(this);
        try {
            
            byte[] bytes = new byte[20];
            SecureRandom.getInstanceStrong().nextBytes(bytes);
            signature.setSignature(bytes);
                       
            return  signature;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SimpleCompany.class.getName()).log(Level.SEVERE, null, ex);
        }
        return signature;
    }

      
  
}
