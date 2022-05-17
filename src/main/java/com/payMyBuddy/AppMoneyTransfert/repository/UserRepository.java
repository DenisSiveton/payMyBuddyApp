package com.payMyBuddy.AppMoneyTransfert.repository;

import com.payMyBuddy.AppMoneyTransfert.model.DTO.UserInfo;
import com.payMyBuddy.AppMoneyTransfert.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(value = "SELECT * FROM USER where email = :username", nativeQuery = true)
    public User findByUsername(@Param("username")String username);

    @Query(value = "SELECT balance FROM USER where id = :user_id", nativeQuery = true)
    public Optional<Double> getUserBalanceById(@Param("user_id") Integer id);

    @Query(nativeQuery = true)
    public Iterable<UserInfo> getRecentUsersInfo(@Param("user_id") Integer id);

    @Query(value = "SELECT email\n" +
            "FROM USER\n" +
            "JOIN(SELECT connection_id, emmeteur, user_id AS destinataire\n" +
            "FROM Relationship\n" +
            "JOIN (SELECT connection_id AS id, user_id AS emmeteur FROM Relationship where user_id =:user_id group by id, emmeteur) AS connections\n" +
            "ON connection_id = connections.id\n" +
            "group by connection_id, emmeteur, destinataire\n"+
            "HAVING destinataire != :user_id) AS result\n" +
            "ON id = result.destinataire", nativeQuery = true)
    public Iterable<String> getContactEmailListFromUserId(@Param("user_id") Integer id);

    @Transactional
    @Modifying
    @Query(value ="UPDATE User SET balance = balance - :amount where id =:sender_id ;\n" +
            "UPDATE User SET balance = balance + :amount where id =:receiver_id ;", nativeQuery = true)
    public void updateUsersAccountAfterTransaction(@Param("sender_id") int userId, @Param("receiver_id") int receiverId,
                                                   @Param("amount") double amountTransaction);

    @Query(value = "SELECT first_name FROM USER where id = :user_id", nativeQuery = true)
    public Optional<String> getUserNameById(@Param("user_id") Integer id);


}
