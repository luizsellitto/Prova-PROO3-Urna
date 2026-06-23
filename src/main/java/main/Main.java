package main;

import application.CandidateRepository;
import application.PrepareElectionService;
import application.TallyService;
import application.VoteService;
import domain.Office;
import infra.DatabaseBuilder;
import infra.SQLiteCandidateRepository;
import utils.CsvUtils;

public class Main {
    public static void main(String[] args) {
        DatabaseBuilder.build();

        CandidateRepository SQLiteCandidateRepository = new SQLiteCandidateRepository();
        PrepareElectionService prepareElectionService = new PrepareElectionService(SQLiteCandidateRepository);
        prepareElectionService.create();
        VoteService voteService = new VoteService(SQLiteCandidateRepository);
        TallyService tallyService = new TallyService(SQLiteCandidateRepository, 2, 70, 94);

        for (int i = 0; i < 100; i++) {
            voteService.vote(CsvUtils.randomCandidateNumber(Office.PRESIDENT.toString()), Office.PRESIDENT);
            voteService.vote(CsvUtils.randomCandidateNumber(Office.GOVERNOR.toString()), Office.GOVERNOR);
            voteService.vote(CsvUtils.randomCandidateNumber(Office.SENATOR.toString()), Office.SENATOR);
            voteService.vote(CsvUtils.randomCandidateNumber(Office.FEDERAL_REPRESENTATIVE.toString()), Office.FEDERAL_REPRESENTATIVE);
            voteService.vote(CsvUtils.randomCandidateNumber(Office.STATE_REPRESENTATIVE.toString()), Office.STATE_REPRESENTATIVE);
        }

        System.out.println("President:\n");
        tallyService.tally(Office.PRESIDENT).forEach(System.out::println);
        System.out.println("\nGovernor:\n");
        tallyService.tally(Office.GOVERNOR).forEach(System.out::println);
        System.out.println("\nSenators:\n");
        tallyService.tally(Office.SENATOR).forEach(System.out::println);
        System.out.println("\nFederal Representatives:\n");
        tallyService.tally(Office.FEDERAL_REPRESENTATIVE).forEach(System.out::println);
        System.out.println("\nState Representatives:\n");
        tallyService.tally(Office.STATE_REPRESENTATIVE).forEach(System.out::println);
    }
}
