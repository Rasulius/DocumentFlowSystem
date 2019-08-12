package document.managment.system.gkumomocikt.test;

import java.util.Date;

/**
 *  @author Rasul
 *  Интерфейс - Document. наследуемся от интерфейса наблюдаемый документ
 *  (шаблон - наблюдатель), для того чтобы документ мог оповестить своих 
 *  подписчиков о тех изменениях, которые с ним произошли.
 *  Методы:
 *       получить компанию создавшая документ 
 *       получить компанию партнера.
 *       получить номер документа
 *       получить подпись создателя и партнера
 *       изменить документ
 *       установить, получить статус документа
 *       получить дату документа
 */
// статус документа
enum DocumentStatus {
    CREATED,
    ONSIGN,
    ONMODIFIED,
    CLOSED; 
}

public interface Document extends DocumentStatusObservable {
    //получить компанию создавшая документ
    public abstract Company getCreatorCompany();
    //получить компанию партнера.
    public abstract Company getPartnerCompany();  
    //получить номер документа
    public abstract String getDocumentID();
    //установить подпись.
    public abstract void setSignature(DocumentSignature signature);      
    //получить подпись создателя и партнера
    public abstract DocumentSignature getCreatorSignature();      
    public abstract DocumentSignature getPartnerSignature(); 
    // изменить документ   
    public abstract boolean changeDocument(Company company);
    //установить статус документа
    public DocumentStatus getDocumentStatus();
    //получить статус документа
    public void setDocumentStatus(DocumentStatus status);    
    //получить дату документа.
    public Date getDocumentDate();           
}
