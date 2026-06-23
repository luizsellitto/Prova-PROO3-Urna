package application;

import domain.Office;

public class VoteService {
    CandidateRepository repository;

    public VoteService(CandidateRepository repository) {
        this.repository = repository;
    }

    public void vote(int number, Office office) {
        if (repository.findByNumberAndOffice(number, office).isEmpty()) {
            repository.incrementVote(0, office);
        } else {
            repository.incrementVote(number, office);
        }
    }
}
