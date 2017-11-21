package app.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import entity.DVD;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Vlad on 18-Nov-17.
 */
@Service
public class QueueService {

    private static final String EXCHANGE_NAME = "DVDs";

    public void publishDVD(DVD dvd) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost("localhost");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        String id = dvd.getId() + "";

        channel.basicPublish(EXCHANGE_NAME,"",null,id.getBytes());

        channel.close();

        connection.close();

    }
}
