package com.payMyBuddy.AppMoneyTransfert.service;

import com.payMyBuddy.AppMoneyTransfert.exception.ConnectionRedundancyException;
import com.payMyBuddy.AppMoneyTransfert.exception.DataNotFoundException;
import com.payMyBuddy.AppMoneyTransfert.model.User;
import com.payMyBuddy.AppMoneyTransfert.repository.ConnectionRepository;
import com.payMyBuddy.AppMoneyTransfert.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectionService {

    @Autowired
    private ConnectionRepository connectionRepository;
    @Autowired
    private UserRepository userRepository;

    public String addConnection(String userEmail, String receiverEmail) throws DataNotFoundException, ConnectionRedundancyException {
        User receiverUser = userRepository.findByUsername(receiverEmail);
        if(receiverUser==null) {
            throw new DataNotFoundException("The given email address does not match any user in our Database. Please try again with another email address");
        }
        else{
            int receiverUserId = receiverUser.getUserId();
            int userId = userRepository.findByUsername(userEmail).getUserId();
            Integer doesConnectionExists = connectionRepository.doesConnectionAlreadyExists(userId, receiverUserId);
            if(doesConnectionExists != null){
                throw new ConnectionRedundancyException("A connection already exists for this email address. Please try again with another email address");
            }else{
                connectionRepository.addConnectionFromTwoUsersId(userId,receiverUserId);
                return receiverEmail;
            }

        }
    }
    //TODO : Implement new methods to deal with deletion and information of connections for next Version
    /*
    public void deleteConnection(Connection connection) {
        connectionRepository.deleteById(connection.getConnectionId());
    }

    public Optional<Connection> getConnectionIdFromTwoUsers(int userId, int receiverId){
        return connectionRepository.getConnectionIdFromTwoUsers(userId, receiverId);

    }*/
}
