package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore implements Store {
    private static final MemStore INST = new MemStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final Map<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger postId = new AtomicInteger();
    private final AtomicInteger candidateId = new AtomicInteger();

    private MemStore() {
        savePost(new Post("Junior Java Job"));
        savePost(new Post("Middle Java Job"));
        savePost(new Post("Senior Java Job"));
        saveCandidate(new Candidate("Junior Java"));
        saveCandidate(new Candidate("Middle Java"));
        saveCandidate(new Candidate("Senior Java"));
    }

    public static MemStore instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    public Collection<User> findAllUsers() {
        return users.values();
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

    public void saveUser(User user) {
        if (user.getId() == 0) {
            user.setId(candidateId.incrementAndGet());
        }
        users.put(user.getId(), user);
    }

    public Post findPostById(int id) {
        return posts.get(id);
    }

    public Candidate findCandidateById(int id) {
        return candidates.get(id);
    }

    public User findUserById(int id) {
        return users.get(id);
    }

    public User findUserByEmail(String email) {
        return users.values().stream().filter(user -> user.getEmail().equals(email)).findFirst().orElse(null);
    }

    public void deleteCandidateById(int id) {
        candidates.remove(id);
    }
}
