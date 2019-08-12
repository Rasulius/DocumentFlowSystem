package document.managment.system.gkumomocikt.test;

import java.util.Date;

/**
 * @author Rasul
 * Фабрика для создания документов.
 * (основная идея использование шаблона factory - это внедрение других
 * типов документов без изменения механизма генерации, 
 * к примеру могут быть XML, SQL, файловые)
 * 
 */
public class DocumentFactory {
    public SimpleDocument createSimpleDocument(Company creator, Company partner,
            Date creationDate){        
        return new SimpleDocument(creator, partner, creationDate);
    }
}
