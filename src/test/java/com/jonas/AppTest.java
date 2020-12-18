package com.jonas;

import com.jonas.dao.AccountDAO;
import com.jonas.domain.Account;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class AppTest {

    private static AccountDAO accountDAO;

    @BeforeClass
    public static void init() {
        accountDAO = new AccountDAO();
    }

    @Test
    public void testSave() {
        try {
            accountDAO.save(buildOne());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBatchSave() {
        try {
            List<Account> accounts = build(5);
            accountDAO.batchSave(accounts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdate() {
        Account account = new Account();
        account.setAccountId("ff3ceb46-6170-414a-be34-2268e38ad1bb");
        account.setBalance(100);
        accountDAO.update(account);
    }

    @Test
    public void testGet() {
        Account account = accountDAO.findOne("ff3ceb46-6170-414a-be34-2268e38ad1bb");
        System.out.println(account);
    }

    @Test
    public void testList() {
        List<Account> accounts = accountDAO.findAll();
        System.out.println(accounts);
    }

    @Test
    public void testReset() {
        List<Account> accounts = build(2);
        accountDAO.reset(accounts);
    }

    private Account buildOne() {
        return build(1).get(0);
    }

    private List<Account> build(int num) {
        Random random = new Random();
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Account account = new Account();
            account.setAccountId(UUID.randomUUID().toString());
            account.setBalance(random.nextInt(1000));
            account.setCtime(new Timestamp(System.currentTimeMillis()));
            account.setUtime(new Timestamp(System.currentTimeMillis()));
            accounts.add(account);
        }
        return accounts;
    }
}
