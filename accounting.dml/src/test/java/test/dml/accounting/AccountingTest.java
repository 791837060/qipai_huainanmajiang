package test.dml.accounting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.dml.accounting.Account;
import com.dml.accounting.AccountingRecord;
import com.dml.accounting.InsufficientBalanceException;
import com.dml.accounting.TextAccountingSummary;

public class AccountingTest {

	@Test
	public void doTest() {
		Account a1 = new Account();
		a1.setId("1");
		assertEquals("", 0, a1.getBalance(), 0);

		AccountingRecord ar = a1.deposit(0.00001, new TextAccountingSummary("测试存款"), 0);
		assertEquals("", 0.00001, ar.getBalanceAfter(), 0);
		assertEquals("", 1, ar.getAccountingNo(), 0);
		assertEquals("", 0.00001, ar.getAccountingAmount(), 0);
		assertEquals("", 0.00001, a1.getBalance(), 0);

		try {
			ar = a1.withdraw(1, new TextAccountingSummary("测试取款"), 0);
			assertTrue(false);
		} catch (InsufficientBalanceException e) {
			assertTrue(true);
		}

		try {
			ar = a1.withdraw(0.000001, new TextAccountingSummary("测试取款"), 0);
			assertEquals("", 0.000009, ar.getBalanceAfter(), 0);
			assertEquals("", 2, ar.getAccountingNo(), 0);
			assertEquals("", -0.000001, ar.getAccountingAmount(), 0);
			assertEquals("", 0.000009, a1.getBalance(), 0);
		} catch (InsufficientBalanceException e) {
			assertTrue(false);
		}

	}

}
