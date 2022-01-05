package service;

import domain.FriendRequest;
import domain.Tuple;
import domain.validators.exceptions.EntityNullException;
import domain.validators.exceptions.NotExistenceException;
import repository.Repository;

public class FriendRequestService implements Service<Tuple<String,String>, FriendRequest> {
    Repository<Tuple<String,String>,FriendRequest> friendRequestRepository;

    public FriendRequestService(Repository<Tuple<String,String>, FriendRequest> friendRequestRepository) {
        this.friendRequestRepository = friendRequestRepository;
    }

    @Override
    public Iterable<FriendRequest> getAll() {
        return friendRequestRepository.getAllEntities();
    }

    @Override
    public void add(FriendRequest friendRequest) {
        friendRequestRepository.save(friendRequest);
    }

    @Override
    public void remove(FriendRequest friendRequest) {

    }

    @Override
    public void update(FriendRequest friendRequest) {
        friendRequestRepository.update(friendRequest);
    }

    @Override
    public FriendRequest findOne(Tuple<String, String> stringStringTuple) throws EntityNullException, NotExistenceException {
        return friendRequestRepository.findOne(stringStringTuple);
    }

}
