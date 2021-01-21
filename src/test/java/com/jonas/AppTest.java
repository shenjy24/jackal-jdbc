package com.jonas;

import com.jonas.dao.UserDAO;
import com.jonas.domain.User;
import com.jonas.util.DateUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class AppTest {

    private static UserDAO userDAO;

    @BeforeClass
    public static void init() {
        userDAO = new UserDAO();
    }

    @Test
    public void testSave() {
        try {
            List<User> users = build(100000);
            for (User user : users) {
                userDAO.save(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBatchSave() {
        try {
            List<User> users = build(500000);
            userDAO.batchSave(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGet() {
        User user = userDAO.findOne("ff3ceb46-6170-414a-be34-2268e38ad1bb");
        System.out.println(user);
    }

    @Test
    public void testList() {
        List<User> users = userDAO.findAll();
        System.out.println(users);
    }

    @Test
    public void testCount() {
        UserDAO.Count count = userDAO.count();
        System.out.println(count);
    }

    @Test
    public void testReset() {
        List<User> users = build(2);
        userDAO.reset(users);
    }

    private User buildOne() {
        return build(1).get(0);
    }

    private List<User> build(int num) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            User user = new User();
            user.setUserName("Jonas" + i);
            user.setUserAge(RandomUtils.nextInt(1, 100));
            user.setUserScore(RandomUtils.nextInt(0, 1000000));
            user.setUserStatus(RandomUtils.nextInt(0, 2));
            user.setCtime(RandomUtils.nextLong(DateUtils.getStampFromTime("2021-01-01 00:00:00"), System.currentTimeMillis()));
            user.setUtime(user.getCtime());
            users.add(user);
        }
        return users;
    }
}
