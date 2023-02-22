package org.jhoffmann.workflowservice.domain;

import spinjar.com.fasterxml.jackson.core.JacksonException;
import spinjar.com.fasterxml.jackson.core.JsonParser;
import spinjar.com.fasterxml.jackson.databind.DeserializationContext;
import spinjar.com.fasterxml.jackson.databind.JsonNode;
import spinjar.com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class OrderItemPOJODeserialiser extends StdDeserializer<OrderItemPOJO> {
/*
{
  "businesskey": "bd132daa-b885-4252-b162-c26aef85a3e9",
  "dishkey": "8760cbc3-5c0d-4ea8-9555-a452308677b9",
  "dishname": "Pizza Napoli",
  "orderId": "7bdd53de-2428-455e-b517-d5ccbfe6d471",
  "amount": 3
}

 */
    public OrderItemPOJODeserialiser() {
        this(null);
    }

    public OrderItemPOJODeserialiser(Class<?> vc) {
        super(vc);
    }

    @Override
    public OrderItemPOJO deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String businesskey = node.get("businesskey").asText();
        String dishkey = node.get("dishkey").asText();
        String dishname = node.get("dishname").asText();
        String orderId = node.get("orderId").asText();
        int amount = node.get("amount").asInt();
        return new OrderItemPOJO(businesskey, dishkey, dishname, orderId, amount);
    }
}
