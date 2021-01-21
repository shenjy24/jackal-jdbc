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
            List<User> users = build(20);
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
        Random random = new Random();
        List<User> users = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            User user = new User();
            user.setUserName("Jonas" + i);
            user.setUserAge(random.nextInt(100));
            user.setUserScore(random.nextInt(1000000));
            user.setUserStatus(random.nextInt(2));
            int time = nextInt(DateUtils.getSecondFromTime("2021-01-20 20:15:00"), DateUtils.getSecondFromTime("2021-02-01 00:00:00"));
            user.setCtime(time * 1000L);
            user.setUtime(time * 1000L);
            users.add(user);
        }
        return users;
    }

    public static int nextInt(int start, int end) {
        return (int) Math.floor(Math.random() * (end - start + 1) + start);
    }
}
