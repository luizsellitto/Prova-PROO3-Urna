package application;

import static utils.CsvUtils.readAllCandidates;

public class PrepareElectionService {
    private final CandidateRepository repo;

    public PrepareElectionService(CandidateRepository repo) {
        this.repo = repo;
    }

    public void create() {
        readAllCandidates().stream()
                .map(c -> new CandidateDTO(
                        Integer.parseInt(c[0]),
                        c[1],
                        c[2],
                        c[3],
                        0))
                .forEach(repo::create);
    }
}
