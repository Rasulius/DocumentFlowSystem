package document.managment.system.gkumomocikt.test;

/**
 * @author Rasul
 * Шаблон наблюдатель.
 * 
 * методы:
 *      регистрация подписчика
 *      удаление подписчика
 *      оповещение подписчиков. 
 */

enum DocumentStateEvent {
    UPDATE_FIRST_SIGN, 
    UPDATE_SECOND_SIGN, 
    DOCUMENT_SIGNED
}

public interface DocumentStatusObservable {
    void registerObserver(DocumentStatusObserver observer);
    void removeObserver(DocumentStatusObserver observer);
    void notifyObservers(DocumentStateEvent event, String documentID);
}
