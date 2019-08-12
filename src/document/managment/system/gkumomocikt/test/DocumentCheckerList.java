package document.managment.system.gkumomocikt.test;

import java.util.LinkedList;

/**
 * @author Rasul
 * Класс содержит список проверяльщиков документа
 * checkAllCkeckers - проверяет все условии, которым соответствует документ
 * 
 */
public class DocumentCheckerList {
    private String error = "";
    LinkedList<DocumentChecker> checkers = new LinkedList<>();
         
    public void add(DocumentChecker checker){
        checkers.add(checker);
    }
    
    public boolean checkAllCheckers(){
        for(DocumentChecker check: checkers){
            if (!check.canBeCreated()){
                error = check.errorMessage();
                return false;
            }               
        }
        return true;
    }    
    public String getError(){
        return error;
    }
    
}
