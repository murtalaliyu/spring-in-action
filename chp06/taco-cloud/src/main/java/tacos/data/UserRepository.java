package tacos.data;

import org.springframework.data.repository.CrudRepository;

import tacos.User;

public interface UserRepository extends CrudRepository<User, Long> {

  public User findByUsername(String username);
  
}
