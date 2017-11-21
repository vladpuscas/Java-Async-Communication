package app;

import app.service.QueueService;


/**
 * Created by Vlad on 18-Nov-17.
 */

public class FileConsumer {


    public static void main(String[] args) throws Exception {
        QueueService queueService = new QueueService();
        queueService.processMessages();
    }
}
