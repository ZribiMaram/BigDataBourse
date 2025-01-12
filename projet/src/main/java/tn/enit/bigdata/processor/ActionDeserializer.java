package tn.enit.bigdata.processor;

import com.fasterxml.jackson.databind.ObjectMapper;

import tn.enit.bigdata.entity.BourseAction;

import org.apache.kafka.common.serialization.Deserializer;

import java.util.Arrays;
import java.util.Map;


public class ActionDeserializer implements Deserializer<BourseAction> {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public BourseAction fromBytes(byte[] bytes) {
        try {
            System.out.println("Deserializing byte array: " + Arrays.toString(bytes)); // Log the raw byte data
            return objectMapper.readValue(bytes, BourseAction.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public BourseAction deserialize(String s, byte[] bytes) {
        return fromBytes((byte[]) bytes);
    }

    @Override
    public void close() {

    }
}
