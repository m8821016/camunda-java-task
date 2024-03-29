package com.task;

import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.topic.TopicSubscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChargeCardWorker {
    private final static Logger LOGGER = LoggerFactory.getLogger(ChargeCardWorker.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Task begin...");
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8080/engine-rest")
                .asyncResponseTimeout(10000) // long polling timeout
                .build();

        // subscribe to an external task topic as specified in the process
        final TopicSubscription open = client.subscribe("charge-card")
                .lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
                .handler((externalTask, externalTaskService) -> {
                    // Get a process variable
                    String item = (String) externalTask.getVariable("item");
                    Long amount = (Long) externalTask.getVariable("amount");

                    // Put your business logic here
                    LOGGER.info("Charging credit card with an amount of '" + amount + "'€ for the item '" + item + "'...");

                    // Complete the task
                    externalTaskService.complete(externalTask);
                })
                .open();
    }
}