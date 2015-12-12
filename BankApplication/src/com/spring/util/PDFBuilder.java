package com.spring.util;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.spring.model.BankStatementTransaction;
 
/**
 * 
 * @author Rajesh
 *
 */
public class PDFBuilder extends AbstractITextPdfView {
 
    @SuppressWarnings("unchecked")
	@Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // get data model which is passed by the Spring container
        List<BankStatementTransaction> listBankStatementTransaction = (List<BankStatementTransaction>) model.get("bankStatementTransactionList");
        
        // Add Creation Date
        doc.addCreationDate();
        
        // Add Header Footer
        HeaderFooterPageEvent event = new HeaderFooterPageEvent();
        writer.setPageEvent(event);
        
        // Add header
        doc.add(new Paragraph("                                                                  Bank Statement"));
        	
        // Add username
        doc.add(new Paragraph("Username: " + request.getSession().getAttribute("sUserName")));
        
        // Checking Account
        Integer checkingAccount = null;
        if (request.getSession().getAttribute("sCheckingAccount") != null){
	        checkingAccount = (Integer) (request.getSession().getAttribute("sCheckingAccount"));
	        doc.add(new Paragraph("Checking Account No.: " + checkingAccount.toString()));
	        request.getSession().removeAttribute("sCheckingAccount");
        }
        // Saving Account
        Integer savingAccount = null;
        if(request.getSession().getAttribute("sSavingAccount") != null){
	        savingAccount = (Integer) (request.getSession().getAttribute("sSavingAccount"));
	        doc.add(new Paragraph("Saving Account No.: " + savingAccount.toString()));
	        request.getSession().removeAttribute("sSavingAccount");
        }
	    
        // Check if transaction list is empty or not
        if(listBankStatementTransaction.isEmpty()){
        	doc.add(new Paragraph("\n"));
        	doc.add(new Paragraph("We're sorry. There's no transactions to display."));
        }else{
	        
	        PdfPTable table = new PdfPTable(5);
	        table.setWidthPercentage(100.0f);
	        table.setWidths(new float[] {1.0f, 2.0f, 3.0f, 2.0f, 2.0f});
	        table.setSpacingBefore(10);
	         
	        // define font for table header row
	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
	        font.setColor(BaseColor.WHITE);
	         
	        // define table header cell
	        PdfPCell cell = new PdfPCell();
	        cell.setBackgroundColor(BaseColor.BLUE);
	        cell.setPadding(5);
	         
	        // write table header
	        cell.setPhrase(new Phrase("No", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Date", font));
	        table.addCell(cell);
	 
	        cell.setPhrase(new Phrase("Description", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Transaction Type", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Amount", font));
	        table.addCell(cell);
	         
	        int i = 0;
	        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
	        String transactionDateString = null;
	        
	        // write table row data
	        for (BankStatementTransaction bankStatementTransaction : listBankStatementTransaction) {
	            table.addCell(((Integer) i).toString());
	            transactionDateString = sdf.format(bankStatementTransaction.getDate());
	            table.addCell(transactionDateString);
	            table.addCell(bankStatementTransaction.getDescription());
	            table.addCell(bankStatementTransaction.getTransactionType().getValue());
	            table.addCell(bankStatementTransaction.getTransactionAmount().toString());
	            i++;
	        }
	         
	        doc.add(table);
        }
         
    }
 
}