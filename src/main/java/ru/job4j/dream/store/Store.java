package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;

public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    Collection<User> findAllUsers();

    void savePost(Post post);

    void saveCandidate(Candidate candidate);

    User createUser(User user);

    Post findPostById(int id);

    Post findPostByName(String name);

    Candidate findCandidateById(int id);

    Candidate findCandidateByName(String name);

    User findUserById(int id);

    User findUserByEmail(String email);

    void deleteCandidateById(int id);
}
