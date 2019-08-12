package document.managment.system.gkumomocikt.test;

/**
 *  @author Rasul
 *  Генератор уникальных номеров для документов. 
 *  был использован из-за медленной работы стандартного класса 
 *  генерации UID.Генерируется номер формата 000001.
 */
public final class GeneratorID {
    static Integer id =1;
    
    public static String generateID(){
       return String.format("%06d", id++);
    }   
}
