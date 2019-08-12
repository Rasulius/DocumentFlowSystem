/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document.managment.system.gkumomocikt.test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Rasul
 */
public class DocumentFlowSystemTest {
    private Company creator;
    private Company partner;
    private Document document;
    private DocumentFlowSystem instance;
    private Date date;
    
    public DocumentFlowSystemTest() {
        creator = new SimpleCompany("Юкос");
        partner = new SimpleCompany("Google inc");
        date = new Date(100000);
        instance = new SimpleDocumentFlowSystem();
        ((SimpleDocumentFlowSystem)instance).printWorkflowSettings();
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() { 
        
        Calendar calendar = new GregorianCalendar();
        calendar.set(2019, 1, 1, 10, 10, 20);
        date = calendar.getTime();                           
    }   
    
    @After
    public void tearDown() {
    }
    
   // устанавливаем время 10ч 20 минут.
    // и пробуем модифицировать.
    
    @Test
    public void testNightEditDocument(){
       System.out.println("------------------------------------------------");
       System.out.println("---Тест на модификацию в нерабочее время------------");
       System.out.println("------------------------------------------------");
       Calendar calendar = new GregorianCalendar();
       calendar.set(2019, 1, 1, 22, 10, 20);
       date = calendar.getTime();               
       document = new SimpleDocument(creator, partner, date);
       ((SimpleDocumentFlowSystem)instance).setTime(date);
       instance.addDocument(document);
       instance.changeDocument(document, creator);
       System.out.println();
    }
    
    // Тест на создание более 10 докуметооборотов.
    // создаем документы с 8 часов, через каждый час.
    @Test
    public void testCreateTenDocumentsOneCompanyCheck(){
        System.out.println("------------------------------------------------");
        System.out.println("-Проверка на участие более чем в 10 документооборотов--");
        System.out.println("------------------------------------------------");
        creator = new SimpleCompany("Компания");          
        Calendar calendar = new GregorianCalendar();              
        for(int i = 0; i<10; i++){    
           partner = new SimpleCompany("Партнер " + i);
           calendar.set(2019, 1, 1, 8 + i, 10, 20);    
           date = calendar.getTime();   
           document = new SimpleDocument(creator, partner, date); 
           instance.addDocument(document);    
        }   
        System.out.println();
    }
    @Test
    public void testCreateFivedocumentsPerHour(){
        System.out.println("------------------------------------------------"); 
        System.out.println("---Тест на создание больше 5 документов в час---");
        System.out.println("------------------------------------------------");
        Calendar calendar = new GregorianCalendar();
        calendar.set(2019, 1, 1, 8, 10, 20);
        
        for(int i = 0; i < 6; i++){
            partner = new SimpleCompany("Партнер " + i);
            document = new SimpleDocument(creator, partner, date);
            instance.addDocument(document); 
        }  
        System.out.println();         
    }
    @Test
    public void testMoreTwoForkflowWithToCompany(){
          System.out.println("------------------------------------------------");
          System.out.println("Тест на ведение больше 2 документооборотов фирмами");
          System.out.println("------------------------------------------------");
        
          Calendar calendar = new GregorianCalendar();
          calendar.set(2019, 1, 1, 8, 10, 20);
          for(int i = 0; i<6; i++){            
             document = new SimpleDocument(creator, partner, date);
             instance.addDocument(document); 
          }  
          System.out.println();         
    }
    
}
