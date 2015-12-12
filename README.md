# CSE-545-Fall-2015

World's most secure banking system.

Instruction for latest database

1. CREATE DATABASE southwesttech.

2. USE southwesttech;

3. then import->
CSE-545-Fall-2015/SQL_Scripts/TestSQL.sql

4. Chnage email and username according to need in Bankuser table.


Transaction Types->

In banktransaction database if

1. Both benefactor and recipient are present -> TRANSFER transaction -> OTP needed

2. Only benefactor present -> DEBIT transaction -> OTP Not needed

3. Only recipient present -> CREDIT transaction -> OTP Not needed

4. We don't have way to track transactions like transfer from saving to checking and vice versa of own accounts. So these are not stored in tranaction table.

Transaction Status Types->

Refer  /BankApplication/src/com/spring/util/TransactionStatus.java

	SUBMITTED(0),
	VALIDATED(1),
	NONVALIDATED(2),
	APPROVED(3), 
	DENIED(4);
	
	SUBMITTED -> just submited from external customer
	
	VALIDATED -> OTP verified status
	
	NONVALIDATED -> Wrong OTP verified status
	
	APPROVED -> When internal employee approved
	
	DENIED -> when internal employee reject
	
