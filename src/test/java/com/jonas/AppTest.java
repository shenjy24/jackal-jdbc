package com.jonas;

import com.jonas.dao.AccountDAO;
import com.jonas.dao.BigNumberDAO;
import com.jonas.dao.GameNumberDAO;
import com.jonas.domain.Account;
import com.jonas.domain.BigNumber;
import com.jonas.domain.GameNumber;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private static AccountDAO accountDAO;
    private static GameNumberDAO gameNumberDAO;
    private static BigNumberDAO bigNumberDAO;

    @BeforeClass
    public static void init() {
        accountDAO = new AccountDAO();
        gameNumberDAO = new GameNumberDAO();
        bigNumberDAO = new BigNumberDAO();
    }

    @Test
    public void testSave() {
        try {
            accountDAO.save(new Account(null, "张三", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSaveNumber() {
        for (int i = 0; i < 1000000; i++) {
            GameNumber gameNumber = new GameNumber(i + "", i + "", i, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
            gameNumberDAO.save(gameNumber);
        }
    }

    @Test
    public void testSaveBigNumber() {
        for (int i = 0; i < 1000000; i++) {
            BigNumber bigNumber = new BigNumber(String.valueOf(i), i);
            bigNumberDAO.save(bigNumber);
        }
    }

    @Test
    public void testCountBigNumber() {
        System.out.println(bigNumberDAO.count());
    }

    @Test
    public void testDelete() {
        accountDAO.delete("sur-1@(200,115,0)");
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
