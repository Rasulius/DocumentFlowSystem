package document.managment.system.gkumomocikt.test;

import java.util.Date;

/**
 * * @author Rasul
 * Интерфейс - Company 
 * Интерфейс компания. наследуется от класса наблюдателя(для отслеживания 
 * за статусом документа).
 * методы: 
 *      получить имя компании
 *      создать документ
 *      подписать документ
 *      генерация подписи
 *      зарегистрировать документ у партнера 
 */
public interface Company  extends DocumentStatusObserver{    
    //получить имя компании
    public abstract String getName();
    //создать документ
    public abstract Document createDocument(Company parther, Date  creationDate);
    // подписать документ
    public abstract void signDocument(Document document);
    //генерация подписи
    public abstract DocumentSignature generateSignature(); 
    //зарегистрировать документ у партнера           
    public abstract void registerPartnerDocument(Document document);    
}
