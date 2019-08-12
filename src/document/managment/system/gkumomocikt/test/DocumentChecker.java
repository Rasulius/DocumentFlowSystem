/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
