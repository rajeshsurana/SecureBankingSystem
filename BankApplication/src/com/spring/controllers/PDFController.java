package com.spring.controllers;
/**
 * 
 * @author Rajesh
 *
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spring.model.BankAccount;
import com.spring.model.BankStatementTransaction;
import com.spring.model.BankTransaction;
import com.spring.model.BankUser;
import com.spring.service.BankAccountService;
import com.spring.service.BankTransactionService;
import com.spring.service.BankUserService;
import com.spring.util.TransactionStatus;
import com.spring.util.TransactionType;
 
@Controller
public class PDFController {
	
	@Autowired
	private BankUserService bankUserService;
	
	@Autowired
	private BankTransactionService bankTransactionService;
	
	@Autowired
	private BankAccountService bankAccountService;
	
	@Autowired
	public void setUserService(BankUserService userServ) {
		this.bankUserService = userServ;
	}
	
	@Autowired
	public void setBankTransactionService(BankTransactionService bankTransactionService) {
		this.bankTransactionService = bankTransactionService;
	}
	
	@Autowired
	public BankAccountService getBankAccountService() {
		return bankAccountService;
	}
	@Autowired
	public void setBankAccountService(BankAccountService bankAccountService) {
		this.bankAccountService = bankAccountService;
	}
	
    /**
     * Handle request to download a PDF document
     */
    @RequestMapping(value = "/downloadPDF", method = RequestMethod.GET)
    public ModelAndView downloadExcel(HttpServletRequest request) {
    	BankUser user = bankUserService.findUserByUserName((String) (request.getSession().getAttribute("sUserName")));
    	List<BankTransaction> bankTransactionList = bankTransactionService.getTransactionList(user.getBankUserId());
    	List<BankStatementTransaction> bankStatementTransactionList = null;
    	
    	if(bankTransactionList != null){
    		bankStatementTransactionList = new ArrayList<BankStatementTransaction>();
    		
    		for(BankTransaction transaction : bankTransactionList){
    			
    			// Show only if approved.
    			if(transaction.getReftransactionstatus().getRefTransactionStatusId() != TransactionStatus.APPROVED.getValue()){
    				continue;
    			}
    			BankStatementTransaction bankStatementTransaction = new BankStatementTransaction();
    			BankUser tmpRecipient = transaction.getRecipient();
    			BankUser tmpBenefactor = transaction.getBenefactor();
    			Float transactionAmount = transaction.getTransactionAmount();
    			Date transactionDate = transaction.getTransactionApprovedOn();
    			String transactionDateString = null;
    			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
    			
    			// TODO: temporary workaround till internal user part is implemented
    			if(transactionDate ==  null){
    				transactionDate = new java.util.Date(); 
    				//continue;
    			}
    			transactionDateString = sdf.format(transactionDate);
    			
    			// Set Transaction Type and Description
    			if(tmpRecipient.getUserName() == "transactionBot"){
    				bankStatementTransaction.setTransactionType(TransactionType.CREDIT);
    				bankStatementTransaction.setDescription(transactionAmount.toString() 
    														+ " credited on "
    														+ transactionDateString);
    			}else if(tmpBenefactor.getUserName() == "transactionBot"){
    				bankStatementTransaction.setTransactionType(TransactionType.DEBIT);
    				bankStatementTransaction.setDescription(transactionAmount.toString() 
    														+ " debited on "
    														+ transactionDateString);
    			}else {
    				bankStatementTransaction.setTransactionType(TransactionType.TRANSFEREXTERNAL);
    				bankStatementTransaction.setDescription(transactionAmount.toString()
    														+ " transferred on "
    														+ transactionDateString 
    														+ " to " + tmpRecipient.getEmail());
    			}
    			
    			// Set Transaction Amount
    			bankStatementTransaction.setTransactionAmount(transactionAmount);
    			
    			// Set Transaction Date
    			bankStatementTransaction.setDate(transactionDate);	
    			bankStatementTransactionList.add(bankStatementTransaction);
    		}
    	}
    	
    	// Store saving account id in session variable
    	BankAccount savingAccount = bankAccountService.findSavingAccountByUserId(user.getBankUserId());
    	if(savingAccount != null){
    		request.getSession().setAttribute("sSavingAccount", savingAccount.getBankAccountId());
    	}
    	

    	// Store checking account id in session variable
    	BankAccount checkingAccount = bankAccountService.findCheckingAccountByUserId(user.getBankUserId());
    	if(checkingAccount != null){
    		request.getSession().setAttribute("sCheckingAccount", checkingAccount.getBankAccountId());
    	}
    	
        // return a view which will be resolved by an excel view resolver
        return new ModelAndView("pdfView", "bankStatementTransactionList", bankStatementTransactionList);
    }
}
