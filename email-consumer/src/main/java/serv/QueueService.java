package serv;

import com.rabbitmq.client.*;
import entity.DVD;
import entity.Email;
import mailconsumer.dao.DVDDao;
import mailconsumer.dao.EmailDao;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

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

                EmailDao emailDao = new EmailDao(new Configuration().configure().buildSessionFactory());

                DVD dvd = dvdDao.getDVD(id);

                List<Email> emails = emailDao.getEmails();

                MailService mailService = new MailService("pstattooparlor@gmail.com","");

                for(Email e: emails) {
                    mailService.sendMail(e.getEmail(),"New DVD", dvd.toString());
                }





            }
        };



        channel.basicConsume(queueName, true, consumer);
    }
}
