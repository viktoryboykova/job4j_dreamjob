package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore implements Store {
    private static final MemStore INST = new MemStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final Map<Integer, User> users = new ConcurrentHashMap<>();
    private final Map<Integer, City> cities = new ConcurrentHashMap<>();
    private final AtomicInteger postId = new AtomicInteger();
    private final AtomicInteger candidateId = new AtomicInteger();

    private MemStore() {
        savePost(new Post(1, "Junior Java Job", LocalDateTime.now()));
        savePost(new Post(2, "Middle Java Job", LocalDateTime.now()));
        savePost(new Post(3, "Senior Java Job", LocalDateTime.now()));
        saveCandidate(new Candidate(1, "Junior Java", 1, LocalDateTime.now()));
        saveCandidate(new Candidate(2, "Middle Java", 2, LocalDateTime.now()));
        saveCandidate(new Candidate(3, "Senior Java", 3, LocalDateTime.now()));
    }

    public static MemStore instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    @Override
    public Collection<Post> findPostsForToday() {
        return null;
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    @Override
    public Collection<Candidate> findCandidatesForToday() {
        return null;
    }

    public Collection<User> findAllUsers() {
        return users.values();
    }

    @Override
    public Collection<City> findAllCities() {
        return cities.values();
    }

    public void savePost(Post post) {
        if (post.getId() == 0) {
            post.setId(postId.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    public void saveCandidate(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(candidateId.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    @Override
    public User createUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    public Post findPostById(int id) {
        return posts.get(id);
    }

    @Override
    public Post findPostByName(String name) {
        for (Post post : posts.values()) {
            if (post.getName().equals(name)) {
                return post;
            }
        }
        return null;
    }

    public Candidate findCandidateById(int id) {
        return candidates.get(id);
    }

    @Override
    public Candidate findCandidateByName(String name) {
        for (Candidate candidate : candidates.values()) {
            if (candidate.getName().equals(name)) {
                return candidate;
            }
        }
        return null;
    }

    public User findUserById(int id) {
        return users.get(id);
    }

    public User findUserByEmail(String email) {
        return users.values().stream().filter(user -> user.getEmail().equals(email)).findFirst().orElse(null);
    }

    @Override
    public City findCityById(int id) {
        return cities.get(id);
    }

    public void deleteCandidateById(int id) {
        candidates.remove(id);
    }
}
