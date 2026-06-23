package application;

import java.util.List;

import static utils.CsvUtils.readAllCandidates;

public class PrepareElectionService {
    CandidateRepository repo;

    public PrepareElectionService(CandidateRepository repo) {
        this.repo = repo;
    }

    public void create() {
        readAllCandidates().forEach(c -> repo.create(
                        new CandidateDTO(
                        Integer.parseInt(c[0]),
                        c[1],
                        c[2],
                        c[3],
                        0
                )));
    }
}
