package entity;

import java.sql.SQLException;

public class MainDao {

    public static void main(String[] args) throws SQLException {

        UserDao userDao = new UserDao();

//        User user1 = new User();
//        user1.setUserName("user1");
//        user1.setEmail("email1");
//        user1.setPassword("password1");
//        userDao1.create(user1);

//        User user2 = new User();
//        user2.setUserName("user287");
//        user2.setEmail("email2098");
//        user2.setPassword("password2567");
//        userDao.create(user2);
//
////        userDao.read(54);
//
//        User userToUpdate = userDao.read(1);
//        userToUpdate.setUserName("Arkadiusz");
//        userToUpdate.setEmail("arek@coderslab.pl");
//        userToUpdate.setPassword("superPassword");
//        userDao.update(userToUpdate);

        User[] all = userDao.findAll();
        for (User u : all) {
            System.out.println(u);
        }
    }




}

