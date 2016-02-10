package pl.allegro.tech.hermes.management.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.avro.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.allegro.tech.hermes.common.message.wrapper.AvroMessageContentWrapper;
import pl.allegro.tech.hermes.common.message.wrapper.JsonMessageContentWrapper;
import pl.allegro.tech.hermes.common.message.wrapper.MessageContentWrapper;
import pl.allegro.tech.hermes.domain.topic.schema.SchemaRepository;

import java.time.Clock;

@Configuration
@EnableConfigurationProperties(MessageProperties.class)
public class MessageConfiguration {

    @Autowired
    MessageProperties messageProperties;

    @Autowired
    SchemaRepository<Schema> avroSchemaRepository;

    @Autowired
    Clock clock;

    @Autowired
    ObjectMapper objectMapper;

    @Bean
    MessageContentWrapper messageContentWrapper() {
        return new MessageContentWrapper(jsonMessageContentWrapper(), new AvroMessageContentWrapper(clock), avroSchemaRepository);
    }

    private JsonMessageContentWrapper jsonMessageContentWrapper() {
        return new JsonMessageContentWrapper(messageProperties.getContentRoot(), messageProperties.getMetadataContentRoot(), objectMapper);
    }

}
