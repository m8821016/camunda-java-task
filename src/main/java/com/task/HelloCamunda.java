package com.task;

import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.topic.TopicSubscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloCamunda {

    private final static Logger LOGGER = LoggerFactory.getLogger(HelloCamunda.class.getName());

    public static void main(String[] args) {
        ExternalTaskClient client = ExternalTaskClient.create().baseUrl("http://localhost:8080/engine-rest").asyncResponseTimeout(10000).build();
        TopicSubscription subscription = client.subscribe("TOPIC_SAY_HELLO")
                .lockDuration(1000)
                .handler((externalTask, externalTaskService) -> {
                    String name = externalTask.getVariable("name");
                    LOGGER.info("Hello Camunda {}", name);
                    externalTaskService.complete(externalTask);
                })
                .open();
    }
}
