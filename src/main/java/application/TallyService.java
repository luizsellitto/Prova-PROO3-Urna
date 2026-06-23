package application;

import domain.Candidate;
import domain.Election;
import domain.Office;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class TallyService {
    CandidateRepository repository;
    int presidentChair = 1;
    int governorChair = 1;
    int senateChairs;
    int federalChairs;
    int stateChairs;
    Election election;

    public TallyService(CandidateRepository repository, int senateChairs, int federalChairs, int stateChairs) {
        this.repository = repository;
        this.senateChairs = senateChairs;
        this.federalChairs = federalChairs;
        this.stateChairs = stateChairs;

        this.election = new Election(LocalDate.now().getYear(), presidentChair, governorChair, senateChairs, stateChairs, federalChairs);
        List<CandidateDTO> candidatos = repository.findAll();
        for (CandidateDTO candidato : candidatos) {
            election.addCandidate(candidato.number(), candidato.name(), candidato.partyName(), Office.valueOf(candidato.runningOffice()), candidato.votes());
        }
    }

    public List<Candidate> tally(Office office) {
        return election.getCandidatesByOffice(office).stream()
                .sorted(Comparator.reverseOrder())
                .limit(getChairsByOffice(office))
                .toList();
    }

    private int getChairsByOffice(Office office) {
        return switch (office) {
            case PRESIDENT -> presidentChair;
            case GOVERNOR -> governorChair;
            case SENATOR -> senateChairs;
            case FEDERAL_REPRESENTATIVE -> federalChairs;
            case STATE_REPRESENTATIVE -> stateChairs;
        };
    }
}
