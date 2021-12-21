package service;

import domain.User;
import domain.validators.ValidationException;
import domain.validators.exceptions.EntityNullException;
import domain.validators.exceptions.ExistenceException;
import domain.validators.exceptions.NotExistenceException;
import repository.Repository;

public class UserService implements Service<String, User> {
    private Repository<String,User> userRepository;

    public UserService(Repository<String,User> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Iterable<User> getAll() {
        return userRepository.getAllEntities();
    }

    @Override
    public void add(User e) throws ValidationException, EntityNullException, ExistenceException {
        this.userRepository.save(e);
    }

    @Override
    public void remove(User e) throws EntityNullException, NotExistenceException{
        this.userRepository.delete(e.getId());
    }

    @Override
    public User findOne(String id) throws EntityNullException, NotExistenceException {
        return userRepository.findOne(id);
    }

    @Override
    public void update(User newUser) throws EntityNullException, NotExistenceException, ValidationException{
        userRepository.update(newUser);
    }
}
