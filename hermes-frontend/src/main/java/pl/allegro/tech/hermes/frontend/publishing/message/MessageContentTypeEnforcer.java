package pl.allegro.tech.hermes.frontend.publishing.message;

import org.apache.avro.Schema;
import pl.allegro.tech.hermes.common.message.wrapper.UnsupportedContentTypeException;
import tech.allegro.schema.json2avro.converter.JsonAvroConverter;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static pl.allegro.tech.hermes.api.AvroMediaType.AVRO_BINARY;

public class MessageContentTypeEnforcer {

    private final JsonAvroConverter converter = new JsonAvroConverter();

    private static final String APPLICATION_JSON_WITH_DELIM = APPLICATION_JSON + ";";
    private static final String AVRO_BINARY_WITH_DELIM = AVRO_BINARY + ";";

    public byte[] enforceAvro(String payloadContentType, byte[] data, Schema schema) {
        if (isJSON(payloadContentType)) {
            return converter.convertToAvro(data, schema);
        } else if (isAvro(payloadContentType)) {
            return data;
        } else {
            throw new UnsupportedContentTypeException(payloadContentType, schema);
        }
    }

    private boolean isJSON(String contentType) {
        return isOfType(contentType, APPLICATION_JSON, APPLICATION_JSON_WITH_DELIM);
    }

    private boolean isAvro(String contentType) {
        return isOfType(contentType, AVRO_BINARY, AVRO_BINARY_WITH_DELIM);
    }

    private boolean isOfType(String contentType, String expectedContentType, String expectedWithDelim) {
        return contentType != null && (contentType.length() > expectedContentType.length() ?
                contentType.startsWith(expectedWithDelim) : contentType.equals(expectedContentType));
    }
}
