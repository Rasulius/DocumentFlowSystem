/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document.managment.system.gkumomocikt.test;

import java.util.LinkedList;

/**
 *  @author Rasul
 * Проверяет ведения более 2ух документооборотов между двумя компаниями.
 * 
 */
public class CompanyWorkflowCountChecker implements DocumentChecker{
    
    private int companyWorkflowCount;
    private Document document;   
    private LinkedList<Document> documentList;
    
    public CompanyWorkflowCountChecker(int workflowCount, Document newDocument,
            LinkedList<Document> documents){
        companyWorkflowCount = workflowCount;
        document = newDocument;
        this.documentList = documents;   
    }
    
    @Override
    public boolean canBeCreated() {
        String companyName = document.getCreatorCompany().getName();
        String partnerName = document.getPartnerCompany().getName();
        int count = 0;
        
        for(Document iter: documentList){
            String iterCompanyName = iter.getCreatorCompany().getName();
            String iterPartnerName = iter.getPartnerCompany().getName();
            
            if (companyName.equalsIgnoreCase(iterCompanyName)
                    && partnerName.equalsIgnoreCase(iterPartnerName))
                count++;
            
            if (companyName.equalsIgnoreCase(iterPartnerName)
                    && partnerName.equalsIgnoreCase(iterCompanyName))
                count++;
        
        }
        
        return count <= companyWorkflowCount;
    }

    @Override
    public String errorMessage() {
       return "нельзя учавствовать более чем " + companyWorkflowCount 
               + " документооборотах между 2 компанями";  
    }
    
}
