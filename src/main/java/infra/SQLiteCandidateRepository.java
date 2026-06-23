package infra;

import application.CandidateDTO;
import application.CandidateRepository;
import domain.Office;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SQLiteCandidateRepository implements CandidateRepository {
    @Override
    public void create(CandidateDTO candidate) {
        String sql = "INSERT INTO candidates (number, name, running_office, party, votes) VALUES (?, ?, ?, ?, ?)";
        try (var stmt = ConnectionFactory.preparedStatement(sql)) {
            stmt.setInt(1, candidate.number());
            stmt.setString(2, candidate.name());
            stmt.setString(3, candidate.runningOffice());
            stmt.setString(4, candidate.partyName());
            stmt.setLong(5, candidate.votes());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<CandidateDTO> findByNumberAndOffice(Integer number, Office office) {
        String sql = "SELECT * FROM candidates WHERE number = ? and running_office = ?";
        try (var stmt = ConnectionFactory.preparedStatement(sql)) {
            stmt.setInt(1, number);
            stmt.setString(2, office.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new CandidateDTO(
                        rs.getInt("number"),
                        rs.getString("name"),
                        rs.getString("running_office"),
                        rs.getString("party"),
                        rs.getInt("votes")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void incrementVote(Integer number, Office office) {
        String sql = "UPDATE candidates SET votes = votes + 1 WHERE number = ? AND running_office = ?";
        try (var stmt = ConnectionFactory.preparedStatement(sql)) {
            stmt.setInt(1, number);
            stmt.setString(2, office.toString());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<CandidateDTO> findAll() {
        String sql = "SELECT number, name, running_office, party, votes FROM candidates";
        List<CandidateDTO> result = new ArrayList<>();
        try (var stmt = ConnectionFactory.preparedStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new CandidateDTO(
                        rs.getInt("number"),
                        rs.getString("name"),
                        rs.getString("running_office"),
                        rs.getString("party"),
                        rs.getInt("votes")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}