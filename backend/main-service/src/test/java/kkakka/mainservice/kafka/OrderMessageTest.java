package kkakka.mainservice.kafka;

import static java.util.Collections.singleton;
import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_1;
import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_2;
import static kkakka.mainservice.fixture.TestMember.TEST_MEMBER_01;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kkakka.mainservice.AcceptanceTest;
import kkakka.mainservice.member.auth.ui.dto.SocialProviderCodeRequest;
import kkakka.mainservice.member.member.domain.MemberProviderName;
import kkakka.mainservice.order.application.dto.ProductOrderDto;
import kkakka.mainservice.order.ui.dto.OrderRequest;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@EmbeddedKafka
@ExtendWith(SpringExtension.class)
public class OrderMessageTest extends AcceptanceTest {

    @Value("${spring.kafka.topic}")
    private String ORDER_TOPIC_NAME;
    @Value("${spring.kafka.template-key}")
    public String TEMPLATE_KEY;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker = new EmbeddedKafkaBroker(
            2, true, ORDER_TOPIC_NAME
    );

    private Consumer<String, String> configEmbeddedKafkaConsumer(String topic) {
        final HashMap<String, Object> consumerProperties = new HashMap<>(
                KafkaTestUtils.consumerProps("embedded-consumer", "false", embeddedKafkaBroker));
        final Consumer<String, String> consumer = new DefaultKafkaConsumerFactory<String, String>(
                consumerProperties, new StringDeserializer(), new StringDeserializer()).createConsumer();
        consumer.subscribe(singleton(ORDER_TOPIC_NAME));

        return consumer;
    }

    @DisplayName("주문시 카프카 메시지가 정상적으로 생성되는지 테스트")
    @Test
    void createOrderMessage() {
        // given
        final Consumer<String, String> consumer = configEmbeddedKafkaConsumer(
                ORDER_TOPIC_NAME);
        String accessToken = 액세스_토큰_가져옴();
        
        ProductOrderDto productOrderDto1 = new ProductOrderDto(PRODUCT_1.getId(), 2);
        ProductOrderDto productOrderDto2 = new ProductOrderDto(PRODUCT_2.getId(), 1);
        List<ProductOrderDto> productOrderDtos = new ArrayList<>();
        productOrderDtos.add(productOrderDto1);
        productOrderDtos.add(productOrderDto2);
        OrderRequest orderRequest = new OrderRequest(productOrderDtos);

        //when
        RestAssured.given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderRequest)
                .when()
                .post("/api/orders")
                .then().log().all()
                .extract();
    
        // then
        final ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10L));

        final Iterable<ConsumerRecord<String, String>> record = records.records(ORDER_TOPIC_NAME);
        assertThat(record).isNotNull();

        consumer.close();
    }

    private String 액세스_토큰_가져옴() {
        final SocialProviderCodeRequest request = SocialProviderCodeRequest.create(
                TEST_MEMBER_01.getCode(), MemberProviderName.TEST);

        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/login/token")
                .then().log().all().extract();

        return response.body().jsonPath().get("accessToken");
    }
}
