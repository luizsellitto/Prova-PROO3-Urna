package domain;

import java.util.Objects;

public class Candidate implements Comparable<Candidate>{
    private final int number;
    private final String name;
    private long votes;
    private final Office office;
    private final PoliticalParty politicalParty;

    public Candidate(int number, String name, Office office, PoliticalParty politicalParty, long votes) {
        this.office = office;
        if (politicalParty == null)
            throw new IllegalArgumentException("Partido não pode ser nulo");
        if (number < 0) throw new IllegalArgumentException("Número não pode ser menor que 0");
        this.number = number;
        this.name = name;
        this.politicalParty = politicalParty;
        this.votes = votes;
    }

    @Override
    public int compareTo(Candidate candidate) {
        if (this.number == candidate.number && (this.number == 1 || this.number == 0)) {
            return 0;
        } else if (this.number == 1 || this.number == 0) {
            return -1;
        } else if (candidate.number == 1 || candidate.number == 0) {
            return 1;
        }
        return Long.compare(this.votes, candidate.votes);
    }

    @Override
    public String toString() {
        return String.format("| %d | %s | %s | %s | Votes = %d |",
                number, name, office, politicalParty.getName(), votes);
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public long getVotes() {
        return votes;
    }

    public Office getOffice() {
        return office;
    }

    public PoliticalParty getPoliticalParty() {
        return politicalParty;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
        return number == candidate.number && office == candidate.office && Objects.equals(politicalParty, candidate.politicalParty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, office, politicalParty);
    }
}
