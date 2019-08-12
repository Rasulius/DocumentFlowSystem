package document.managment.system.gkumomocikt.test;

/**
 *  @author Rasul
 * Интерфейс система документооборота.
 * Наследуется от наблюдателя(для отслеживания статуса документов - шаблон observer).
 * методы:
 *      добавить документ
 *      удалить документ
 *      подписать документ компанией.
 *      изменить документ
 *      получить документ по уникальному номеру
 * 
  */
public interface DocumentFlowSystem extends DocumentStatusObserver{
    // добавить документ
    public abstract void addDocument(Document document);
    // удалить документ
    public abstract void remove(Document document);
    // подписать документ
    public abstract void signDocument(Document document, Company company);    
    // изменить документ
    public abstract void changeDocument(Document document, Company company);    
    // найти документ 
    public abstract Document findByID(String documentID);
    
    
}
