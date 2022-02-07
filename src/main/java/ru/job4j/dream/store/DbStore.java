package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class DbStore implements Store {
    private static final DbStore INSTANCE = new DbStore();

    private final BasicDataSource pool = new BasicDataSource();

    private DbStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(
                        DbStore.class.getClassLoader()
                                .getResourceAsStream("db.properties")
                )
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new DbStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(
                            new Post(
                                    it.getInt("id"),
                                    it.getString("name"),
                                    it.getTimestamp("created").toLocalDateTime()
                            ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Collection<Post> findPostsForToday() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post WHERE created BETWEEN ? AND ?")
        ) {
            ps.setTimestamp(1, Timestamp.valueOf(LocalDate.now().atStartOfDay()));
            ps.setTimestamp(2, Timestamp.valueOf(LocalDate.now().plusDays(1).atStartOfDay()));
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(
                            new Post(
                                    it.getInt("id"),
                                    it.getString("name"),
                                    it.getTimestamp("created").toLocalDateTime()
                            ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    public void savePost(Post post) {
        if (post.getId() == 0) {
            createPost(post);
        } else {
            updatePost(post);
        }
    }

    private Post createPost(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO post(name, created) VALUES (?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    private void updatePost(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement statement =
                     cn.prepareStatement("UPDATE post SET name = ? WHERE id = ?")) {
            statement.setString(1, post.getName());
            statement.setInt(2, post.getId());
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Post findPostById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Post(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getTimestamp("created").toLocalDateTime()
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Post findPostByName(String name) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post WHERE name = ?")
        ) {
            ps.setString(1, name);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Post(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getTimestamp("created").toLocalDateTime()
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(
                            new Candidate(
                                    it.getInt("id"),
                                    it.getString("name"),
                                    it.getInt("city_id"),
                                    it.getTimestamp("created").toLocalDateTime()
                            ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidates;
    }

    @Override
    public Collection<Candidate> findCandidatesForToday() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate WHERE created BETWEEN ? AND ?")
        ) {
            ps.setTimestamp(1, Timestamp.valueOf(LocalDate.now().atStartOfDay()));
            ps.setTimestamp(2, Timestamp.valueOf(LocalDate.now().plusDays(1).atStartOfDay()));
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(
                            new Candidate(
                                    it.getInt("id"),
                                    it.getString("name"),
                                    it.getInt("city_id"),
                                    it.getTimestamp("created").toLocalDateTime()
                            ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidates;
    }

    public void saveCandidate(Candidate candidate) {
        if (candidate.getId() == 0) {
            createCandidate(candidate);
        } else {
            updateCandidate(candidate);
        }
    }

    private Candidate createCandidate(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO candidate(name, city_id, created) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getCityId());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidate;
    }



    private void updateCandidate(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement statement =
                     cn.prepareStatement("UPDATE candidate SET name = ?, city_id = ? WHERE id = ?")) {
            statement.setString(1, candidate.getName());
            statement.setInt(2, candidate.getCityId());
            statement.setInt(3, candidate.getId());
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Candidate findCandidateById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Candidate(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getInt("city_id"),
                            it.getTimestamp("created").toLocalDateTime()
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Candidate findCandidateByName(String name) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate WHERE name = ?")
        ) {
            ps.setString(1, name);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Candidate(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getInt("city_id"),
                            it.getTimestamp("created").toLocalDateTime()
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteCandidateById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement statement =
                     cn.prepareStatement("DELETE FROM candidate WHERE id = ?")) {
            statement.setInt(1, id);
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Collection<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM users")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    User user = new User(
                            it.getString("name"),
                            it.getString("email"),
                            it.getString("password")
                    );
                    user.setId(it.getInt("id"));
                    users.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public User createUser(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO users(name, email, password) VALUES (?,?,?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public void updateUser(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement statement =
                     cn.prepareStatement("UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?")) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getId());
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User findUserById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM users WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    User user = new User(
                            it.getString("name"),
                            it.getString("email"),
                            it.getString("password")
                    );
                    user.setId(it.getInt("id"));
                    return user;
                }
            }
        } catch (Exception e) {
            throw new NullPointerException("Пользователь не найден в базе данных");
        }
        return null;
    }

    public User findUserByEmail(String email) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM users WHERE email = ?")
        ) {
            ps.setString(1, email);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    User user = new User(
                            it.getString("name"),
                            it.getString("email"),
                            it.getString("password")
                    );
                    user.setId(it.getInt("id"));
                    return user;
                }
            }
        } catch (Exception e) {
            throw new NullPointerException("Пользователь не найден в базе данных");
        }
        return null;
    }


    @Override
    public Collection<City> findAllCities() {
        List<City> cities = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM city")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    cities.add(new City(
                            it.getInt("id"),
                            it.getString("name")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cities;
    }

    @Override
    public City findCityById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM city WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new City(it.getInt("id"), it.getString("name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
