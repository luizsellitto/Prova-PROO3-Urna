package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Election {
    private final int year;
    private final int presidentChair;
    private final int governorChair;
    private final int senateChairs;
    private final int stateChairs;
    private final int federalChairs;
    private final Map<Integer, PoliticalParty> politicalParties;

    public Election(int year, int presidentChair, int governorChair, int senateChairs, int stateChairs, int federalChairs) {
        this.year = year;
        this.presidentChair = presidentChair;
        this.governorChair = governorChair;
        this.senateChairs = senateChairs;
        this.stateChairs = stateChairs;
        this.federalChairs = federalChairs;
        this.politicalParties = new HashMap<>();
    }

    public void addCandidate(int number, String candidateName, String partyName, Office office, long votes) {
        String candidateNumber = String.format("%02d", number);
        String substring = candidateNumber.substring(0, 2);
        int partyNumber = Integer.parseInt(substring);
        if (!politicalParties.containsKey(partyNumber)) {
            politicalParties.put(partyNumber,
                    new PoliticalParty(partyNumber, partyName));
        }
        PoliticalParty party = politicalParties.get(partyNumber);
        party.addCandidate(new Candidate(number, candidateName, office, party, votes));
    }

    public List<PoliticalParty> partiesList() {
        return politicalParties.values().stream().toList();
    }

    public List<Candidate> getCandidatesByOffice(Office office) {
        return politicalParties.values().stream()
                .flatMap(p->p.getCandidates(office).stream())
                .toList();
    }

    public int getYear() {
        return year;
    }

    public int getSenateChairs() {
        return senateChairs;
    }

    public int getStateChairs() {
        return stateChairs;
    }

    public int getFederalChairs() {
        return federalChairs;
    }

    public Map<Integer, PoliticalParty> getPoliticalParties() {
        return politicalParties;
    }
}
