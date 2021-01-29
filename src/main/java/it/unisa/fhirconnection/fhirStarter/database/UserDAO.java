package it.unisa.fhirconnection.fhirStarter.database;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import it.unisa.fhirconnection.fhirStarter.model.User;

import java.time.LocalTime;
import java.util.ArrayList;


@Repository
public interface UserDAO extends CrudRepository<User, Integer> {
       User findByUsername(String username);
       Boolean existsUsersByToken(String Token);

       @Query(value= "SELECT u FROM User u where time<=u.time+3600000")
       User getUsersByTime(@Param("time")Long time);

}
