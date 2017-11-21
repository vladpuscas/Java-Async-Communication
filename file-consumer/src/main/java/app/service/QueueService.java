package app.service;

import app.dao.DVDDao;
import com.rabbitmq.client.*;
import entity.DVD;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.hibernate.cfg.*;

/**
 * Created by Vlad on 18-Nov-17.
 */
public class QueueService {

    private static final String EXCHANGE_NAME = "DVDs";

    public void processMessages() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost("localhost");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        String queueName = channel.queueDeclare().getQueue();

        channel.queueBind(queueName,EXCHANGE_NAME,"");


        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                int id  = Integer.parseInt(message);

                DVDDao dvdDao = new DVDDao(new Configuration().configure().buildSessionFactory());

                DVD dvd = dvdDao.getDVD(id);

                FileService fileService = new FileService();

                fileService.createFile(dvd);

            }
        };



        channel.basicConsume(queueName, true, consumer);
    }
}
