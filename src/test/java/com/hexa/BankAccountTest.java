package com.hexa;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class BankAccountTest {
    @Test
    public void withdraw_returnsTrue_ifEnoughFunds() {
        BankAccount account = new BankAccount(500);
        assertTrue(account.withdraw(400));
    }
}