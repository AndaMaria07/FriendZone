package repository.db;

import domain.FriendRequest;
import domain.Tuple;
import domain.validators.exceptions.EntityNullException;
import domain.validators.exceptions.ExistenceException;
import domain.validators.exceptions.NotExistenceException;
import repository.Repository;

import java.sql.*;
import java.util.*;

public class FriendRequestDbRepository implements Repository<Tuple<String,String>, FriendRequest> {
    private String url;
    private String username;
    private String password;

    public FriendRequestDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Long getEntitiesCount() {
        return null;
    }

    @Override
    public FriendRequest findOne(Tuple<String,String> id) {
        String sql = "SELECT * FROM friend_requests AS fr WHERE fr.from_user=? AND fr.to_user=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql))
        {
             statement.setString(1, id.getLeft());
             statement.setString(2, id.getRight());
             ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next())
                throw new NotExistenceException();
            else{
                String from_user=resultSet.getString(1);
                String to_user=resultSet.getString(2);
                String status=resultSet.getString(3);
                return new FriendRequest(status,new Tuple<>(from_user,to_user));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<FriendRequest> getAllEntities() {
        Set<FriendRequest> friendRequests = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from friend_requests");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String from = resultSet.getString("from_user");
                String to = resultSet.getString("to_user");
                String status = resultSet.getString("status");
                Tuple<String,String> id=new Tuple<>(from,to);
                FriendRequest friendRequest=new FriendRequest(status,id);
                friendRequests.add(friendRequest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendRequests;
    }

    @Override
    public void save(FriendRequest entity) {
        if (entity == null)
            throw new EntityNullException();

        try {
            findOne(entity.getId());
            throw new ExistenceException();
        }catch(NotExistenceException exc) {
            String sql = "insert into friend_requests(status, from_user, to_user ) values (?, ?, ?)";
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, (entity).getStatus());
                ps.setString(2, (entity).getId().getLeft());
                ps.setString(3, (entity).getId().getRight());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Tuple<String,String> id) {
        findOne(id);
        String sql = "delete from friend_requests AS fr where fr.from_user=? and fr.to_user=? ";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id.getLeft());
            ps.setString(2, id.getRight());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(FriendRequest entity) {
        FriendRequest foundFriendRequest=findOne(entity.getId());
        String sql = "UPDATE friend_requests SET status=? WHERE from_user=? AND to_user=?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, entity.getStatus());
            ps.setString(2, foundFriendRequest.getId().getLeft());
            ps.setString(3, foundFriendRequest.getId().getRight());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
