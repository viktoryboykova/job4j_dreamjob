package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;

public class MainStore {
    public static void main(String[] args) {
        Store store = DbStore.instOf();
        store.saveCandidate(new Candidate(0, "Egor"));
        store.saveCandidate(new Candidate(0, "Vika"));
        for (Candidate candidate : store.findAllCandidates()) {
            System.out.println(candidate.getId() + " " + candidate.getName());
        }
        store.saveCandidate(new Candidate(1, "Senior Egor"));
        System.out.println(store.findCandidateById(1).getName());
        store.deleteCandidateById(2);
    }
}
