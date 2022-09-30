//package com.gamekeys.gameshop.configuration;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import java.time.LocalDate;
//
//@Configuration
//public class ObjectMapperConfiguration {
//
//    @Bean
//    @Primary
//    public ObjectMapper objectMapper() {
//        ObjectMapper mapper = new ObjectMapper();
//
//        // Registro global do serializer/deserializer para datas sem horário
//        SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addSerializer(LocalDate.class, LocalDateSerializer.INSTANCE);
//        simpleModule.addDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE);
//
//        mapper.registerModule(simpleModule);
//        return mapper;
//    }
//}
