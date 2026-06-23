package application;

import domain.Candidate;
import domain.Office;

import java.util.List;
import java.util.Optional;

public interface CandidateRepository {
    void create(CandidateDTO candidate);
    Optional<CandidateDTO> findByNumberAndOffice(Integer number, Office office);
    void incrementVote(Integer number, Office office);
    List<CandidateDTO> findAll();
}
