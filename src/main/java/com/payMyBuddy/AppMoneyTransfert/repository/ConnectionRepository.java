package com.payMyBuddy.AppMoneyTransfert.repository;

import com.payMyBuddy.AppMoneyTransfert.model.Connection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ConnectionRepository extends CrudRepository<Connection, Integer> {

    @Query(value = "SELECT * FROM Connection Where id = (SELECT connection_id FROM (SELECT connection_id FROM\n" +
            "(Select relationship.connection_id, connectionlist.user_id AS emmeteur ,relationship.user_id AS receiver FROM relationship\n" +
            "JOIN\n" +
            "(Select * from relationship where user_id =:sender_id) AS connectionlist\n" +
            "ON relationship.connection_id = connectionlist.connection_id\n" +
            "where relationship.user_id !=:sender_id)AS result\n" +
            "where receiver =:receiver_id) AS result);", nativeQuery = true)
    public Optional<Connection> getConnectionIdFromTwoUsers(@Param("sender_id") int senderId, @Param("receiver_id") int receiverId);

    @Transactional
    @Modifying
    @Query(value = "SET @current_user_id =:user_id ;\n" +
            "SET @new_connection_user_id =:receiver_id ;\n" +
            "\n" +
            "INSERT INTO Connection (date_start)\n" +
            "Values  (curdate());\n" +
            "SET @new_connection_id = (SELECT id FROM Connection ORDER BY id DESC LIMIT 1) ;\n" +
            "\n" +
            "INSERT INTO Relationship VALUES\n" +
            "(@new_connection_id,@current_user_id),(@new_connection_id,@new_connection_user_id);", nativeQuery = true)
    public void addConnectionFromTwoUsersId(@Param("user_id") int senderId, @Param("receiver_id") int receiverId);


    @Query(value ="SELECT connection_id FROM relationship\n" +
            "JOIN (SELECT connection_id as connectionList FROM relationship where user_id =:user_id ) As connectionForUser\n" +
            "ON relationship.connection_id = connectionList\n" +
            "where relationship.user_id =:receiver_id ;", nativeQuery = true)
    public Integer doesConnectionAlreadyExists(@Param("user_id") int userEmail, @Param("receiver_id") int receiverEmail);
}
