package entity;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.Arrays;

public class UserDao {
    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";

    private static final String CHANGE_USER_QUERY = "UPDATE users SET username = ?, email = ?, password = ? where id = ?";

    private static final String READ_USER_QUERY =
            "SELECT * FROM users where id = ?";

    private static final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE id = ?";

    private static final String FIND_ALL_USERS_QUERY =
            "SELECT * FROM users";

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User create(User user) throws SQLException {

        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getUserName());
            ps.setString(2, user.getEmail());
            ps.setString(3, hashPassword(user.getPassword()));
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }

            System.out.println("User was Added toDB " + user.getUserName());
            return user;
        } catch (Exception e) {
            throw new SQLException("Error" + e.getMessage(), e);
        }
    }

    public User read(int id) throws SQLException {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(READ_USER_QUERY);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            } else {
                throw new SQLException("Nie ma takiego idi" + id);
            }
        }
    }

    public void update (User user) {
        try (Connection conn = DbUtil.getConnection()){

            PreparedStatement st = conn.prepareStatement(CHANGE_USER_QUERY);
            st.setString(1, user.getUserName());
            st.setString(2, user.getEmail());
            st.setString(3, hashPassword(user.getPassword()));
            st.setInt(4, user.getId());
            st.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public User[] findAll() {
        try (Connection conn = DbUtil.getConnection()) {
            User[] users = new User[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_USERS_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                users = addToArray(user, users);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1); // Tworzymy kopię tablicy powiększoną o 1.
        tmpUsers[users.length] = u; // Dodajemy obiekt na ostatniej pozycji.
        return tmpUsers; // Zwracamy nową tablicę.
    }

    public void delete(int userId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_USER_QUERY);
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
