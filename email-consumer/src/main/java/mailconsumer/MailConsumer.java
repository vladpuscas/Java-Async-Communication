package mailconsumer;

import serv.QueueService;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Vlad on 18-Nov-17.
 */
public class MailConsumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        QueueService queueService = new QueueService();

        queueService.processMessages();
    }
}
