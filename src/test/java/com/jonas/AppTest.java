package com.jonas;

import com.jonas.dao.AccountDAO;
import com.jonas.domain.Account;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private static AccountDAO accountDAO;

    @BeforeClass
    public static void init() {
        accountDAO = new AccountDAO();
    }

    @Test
    public void testSave() {
        try {
            accountDAO.insert(new Account(null, "张三", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDelete() {
        accountDAO.delete(3L);
    }

    @Test
    public void testUpdate() {
        accountDAO.update(new Account(4L, "李四", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())));
    }

    @Test
    public void testGet() {
        Account account = accountDAO.get(1L);
        System.out.println(account);
    }

    @Test
    public void testList() {
        List<Account> accounts = accountDAO.list();
        System.out.println(accounts);
    }
}
