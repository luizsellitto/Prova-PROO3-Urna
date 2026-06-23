package domain;

import java.util.*;

public class PoliticalParty {
    private final int number;
    private final String name;
    private Candidate president;
    private Candidate governor;
    private final Map<Integer, Candidate> senator;
    private final Map<Integer, Candidate> federalRepresentatives;
    private final Map<Integer, Candidate> stateRepresentatives;


    public PoliticalParty(int number, String name) {
        this.number = number;
        this.name = name;
        this.senator = new HashMap<>();
        this.federalRepresentatives = new HashMap<>();
        this.stateRepresentatives = new HashMap<>();
    }

    public void addCandidate(Candidate c) {
        if (c.getOffice() == Office.PRESIDENT && president == null) {
            president = c;
        } else if (c.getOffice() == Office.GOVERNOR && governor == null) {
            governor = c;
        } else {
            switch (c.getOffice()) {
                case SENATOR -> senator.put(c.getNumber(), c);
                case FEDERAL_REPRESENTATIVE -> federalRepresentatives.put(c.getNumber(), c);
                case STATE_REPRESENTATIVE -> stateRepresentatives.put(c.getNumber(), c);
                default -> {
                }
            }
        }
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public Candidate getPresident() {
        return president;
    }

    public Candidate getGovernor() {
        return governor;
    }

    public List<Candidate> getSenator() {
        return senator.values().stream().toList();
    }

    public List<Candidate> getFederalRepresentatives() {
        return federalRepresentatives.values().stream().toList();
    }

    public List<Candidate> getStateRepresentatives() {
        return stateRepresentatives.values().stream().toList();
    }

    public List<Candidate> getCandidates(Office office) {
        return switch (office) {
            case PRESIDENT -> president != null ? List.of(president) : List.of();
            case GOVERNOR -> governor != null ? List.of(governor) : List.of();
            case SENATOR -> getSenator();
            case FEDERAL_REPRESENTATIVE -> getFederalRepresentatives();
            case STATE_REPRESENTATIVE -> getStateRepresentatives();
        };

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PoliticalParty that = (PoliticalParty) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
