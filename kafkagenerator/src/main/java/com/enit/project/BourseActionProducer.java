package com.enit.project;


import java.util.Date;

import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;


public class BourseActionProducer {

    private final Producer<String, BourseAction> producer;
    private final Random rand = new Random();
    private final String[] actionNames = {
        "TechCorp", "GreenEnergy", "FinancePlus", "HealthInc", "AutoMakers",
        "BioLife", "CloudNine", "EduGrow", "RetailHub", "TravelWorks"
    };
    private final double[] actionPrices = new double[10];
    private final String[] actionIds = new String[10];

    public BourseActionProducer(final Producer<String, BourseAction> producer) {
        this.producer = producer;
        initializeActions();
    }

    public static void main(String[] args) throws Exception {
        Properties properties = PropertyFileReader.readPropertyFile();
        Producer<String, BourseAction> producer = new Producer<>(new ProducerConfig(properties));
        BourseActionProducer bourseActionProducer = new BourseActionProducer(producer);
        bourseActionProducer.generateActionUpdates(properties.getProperty("kafka.topic"));
    }

    private void initializeActions() {
        for (int i = 0; i < actionNames.length; i++) {
            actionIds[i] = UUID.randomUUID().toString(); // Generate unique IDs
            actionPrices[i] = 50 + rand.nextDouble() * 50; // Initial prices between 50 and 100
        }
    }

    private void generateActionUpdates(String topic) throws InterruptedException {
        while (true) {
            for (int i = 0; i < actionNames.length; i++) {
                BourseAction action = generateBourseActionData(i);
                System.out.println("Sent: " + action);
                producer.send(new KeyedMessage<>(topic, action));
            }
            Thread.sleep(rand.nextInt(5000 - 2000) + 2000); // Random delay of 2 to 5 seconds
        }
    }

    private BourseAction generateBourseActionData(int actionIndex) {
        String actionName = actionNames[actionIndex];
        String id = actionIds[actionIndex];
        double currentPrice = actionPrices[actionIndex];

        // Simulate price changes (up or down within a range)
        double priceChange = (rand.nextDouble() - 0.5) * 5; // +/- $2.5 change
        double newPrice = Math.max(10, currentPrice + priceChange); // Ensure price doesn't go below 10
        actionPrices[actionIndex] = newPrice;

        return new BourseAction(id, actionName, newPrice, new Date());
    }
}

 
