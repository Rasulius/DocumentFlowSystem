package document.managment.system.gkumomocikt.test;

/**
 * подпись документа.
 * @author Rasul
 */
public class SimpleDocumentSignature implements DocumentSignature{
    private SimpleCompany company;
    private byte[] sign;
    private boolean hasSignature;
    private final String nonameCompany = "noname";
    
    // Создание сигнатуры без компании.  
    public SimpleDocumentSignature(){
        company = new SimpleCompany(nonameCompany);
        hasSignature = false;
    }   
    
    public SimpleDocumentSignature(SimpleCompany company){
        this.company = company;
        hasSignature = false;
    }
             
    @Override
    public String getOwnerName() {
        return company.getName();
    }

    @Override
    public byte[] getSignature() {
        return sign.clone();
    }

    @Override
    public boolean hasSignature() {
        return hasSignature;
    }

    @Override
    public void setSignature(byte[] signature) {
        if (signature != null){
            sign = signature.clone();
            hasSignature = true;
        }
        hasSignature = false;
    }

    @Override
    public void clearSign() {
        hasSignature = false;        
    }     
    
}
