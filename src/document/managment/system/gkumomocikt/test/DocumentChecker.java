package document.managment.system.gkumomocikt.test;

/**
 *@author Rasul
 * Интерфейс проверялщик документов
 * true если документ можно создать или редактировать и false если нельзя.
 */
public interface DocumentChecker {
    public boolean canBeCreated();// Проверка может ли документ быть созданным
    public String errorMessage();// Получить текст ошибки
}
