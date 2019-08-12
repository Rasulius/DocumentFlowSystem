package document.managment.system.gkumomocikt.test;

/**
 *
 * @author Rasul
 * Интерфейс подпись документа создан для унифицирования подписи документа,
 * подпись представляет собой массив байт.
 * методы:
 *     получить имя владельца подписи
 *     установить подпись
 *     получить подпись
 *     проверить присутствие подписи
 *     очитсть подпись
 * 
 */
public interface DocumentSignature {
    // Получить имя владельца подписи
    public abstract String getOwnerName(); 
    // Установить подпись 
    public abstract void setSignature(byte[] signature);
    // Получить подпись
    public abstract byte[] getSignature(); 
    //Проверка на наличие подписи
    public abstract boolean hasSignature();
    // очистить подпись
    public abstract void clearSign();
}
