package com.enit.project;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

public class BourseActionEncoder implements Encoder<BourseAction> {

    private static ObjectMapper objectMapper = new ObjectMapper();
    public BourseActionEncoder(VerifiableProperties verifiableProperties) {

    }
    public byte[] toBytes(BourseAction event) {
        try {
            String msg = objectMapper.writeValueAsString(event);
            System.out.println(msg);
            return msg.getBytes();
        } catch (JsonProcessingException e) {
            System.out.println("Error in Serialization" +e.getMessage());
        }
        return null;
    }
}