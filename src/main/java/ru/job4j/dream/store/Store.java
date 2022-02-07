package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;

public interface Store {
    Collection<Post> findAllPosts();
    Collection<Post> findPostsForToday();
    void savePost(Post post);
    Post findPostById(int id);
    Post findPostByName(String name);

    Collection<Candidate> findAllCandidates();
    Collection<Candidate> findCandidatesForToday();
    void saveCandidate(Candidate candidate);
    Candidate findCandidateById(int id);
    Candidate findCandidateByName(String name);
    void deleteCandidateById(int id);

    Collection<User> findAllUsers();
    User createUser(User user);
    User findUserById(int id);
    User findUserByEmail(String email);

    Collection<City> findAllCities();
    City findCityById(int id);

}
