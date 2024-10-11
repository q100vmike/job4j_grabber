package ru.job4j.grabber;

import ru.job4j.model.Post;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store {

    private Connection connection;

    private Post getPostFromRS(ResultSet resultSet) throws SQLException {
        return new Post(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("link"),
                resultSet.getString("text"),
                resultSet.getTimestamp("created").toLocalDateTime());
    }

    public PsqlStore(Properties config) throws SQLException {
        try {
            Class.forName(config.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        connection = DriverManager.getConnection(
                config.getProperty("url"),
                config.getProperty("username"),
                config.getProperty("password")
        );
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO post(name, text, link, created) VALUES (?, ?, ?, ?::timestamp) ON CONFLICT(link) DO NOTHING",
                             Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getDescription());
            statement.setString(3, post.getLink());
            statement.setString(4, String.valueOf(post.getCreated()));
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM post")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    list.add(getPostFromRS(resultSet));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Post findById(int id) {
        Post post = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM post WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    post = getPostFromRS(resultSet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) {
        try (InputStream input = PsqlStore.class.getClassLoader()
                .getResourceAsStream("grabber.properties")) {
            Properties config = new Properties();
            config.load(input);
            PsqlStore store = new PsqlStore(config);
            Post post1 = new Post("title1", "www.leningrad.spb.ru", "Sergey Shnurov", LocalDateTime.now().withNano(0));
            Post post2 = new Post("title2", "www.limpbizkit.com", "Fred Durst", LocalDateTime.now().withNano(0));
            Post post3 = new Post("title1", "www.leningrad.spb.ru", "Sergey Shnurov", LocalDateTime.now().withNano(0));
            store.save(post1);
            store.save(post2);
            store.save(post3);
            List<Post> posts = store.getAll();
            posts.forEach(System.out::println);

            Post find = store.findById(1);
            System.out.println(find.getTitle());
            store.close();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}