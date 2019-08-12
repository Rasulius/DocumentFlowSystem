package document.managment.system.gkumomocikt.test;

/**
 *
 * @author Rasul
 * Шаблон наблюдатель.  
 * методы: произошло обновление подписи создателя
 *         произошло обновление подписи партнера 
 */
public interface DocumentStatusObserver {
    void updateSignByCreator(String DocumentID);
    void updateSignByPartner(String DocumentID);    
}



