package kkakka.mainservice.order.application;

import java.util.stream.Collectors;
import kkakka.mainservice.kafka.order.MemberMessage;
import kkakka.mainservice.kafka.order.OrderMessage;
import kkakka.mainservice.kafka.order.ProductMessage;
import kkakka.mainservice.kafka.order.ProductOrderMessage;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.order.domain.Order;
import kkakka.mainservice.order.domain.ProductOrder;
import kkakka.mainservice.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@RequiredArgsConstructor
public class OrderMessageProducer {

    @Value("${spring.kafka.topic}")
    private String ORDER_TOPIC_NAME;
    @Value("${spring.kafka.template-key}")
    public String TEMPLATE_KEY;

    private static final Logger log = LoggerFactory.getLogger(OrderMessageProducer.class);

    private final KafkaTemplate<String, OrderMessage> kafkaTemplate;

    public void sendMessage(Order order) {
        final OrderMessage orderMessage = convertToMessage(order);
        final ListenableFuture<SendResult<String, OrderMessage>> future = kafkaTemplate.send(
                ORDER_TOPIC_NAME, TEMPLATE_KEY, orderMessage);

        future.addCallback(new ListenableFutureCallback<SendResult<String, OrderMessage>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.warn("-------- KafkaError start ---------");
                log.warn("OrderMessageProducer: {}", ex.getMessage());
                log.warn("-------- KafkaError end ---------");
            }

            @Override
            public void onSuccess(SendResult<String, OrderMessage> result) {
                log.debug("OrderMessageProducer message produced");
            }
        });
    }

    private OrderMessage convertToMessage(Order order) {
        return orderMessage(order);
    }

    @NotNull
    private OrderMessage orderMessage(Order order) {
        return OrderMessage.create(
                order.getId(),
                order.getOrderedAt(),
                order.getTotalPrice(),
                order.getProductOrders()
                        .stream()
                        .map(productOrder -> productOrderMessage(productOrder))
                        .collect(Collectors.toList()),
                memberMessage(order.getMember())
        );
    }

    private ProductOrderMessage productOrderMessage(ProductOrder productOrder) {
        return ProductOrderMessage.create(
                productOrder.getId(),
                productOrder.getPrice(),
                productOrder.getQuantity(),
                productOrder.getDeleted(),
                productMessage(productOrder.getProduct()));
    }

    private ProductMessage productMessage(Product product) {
        return ProductMessage.create(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                product.getDiscount()
        );
    }

    private MemberMessage memberMessage(Member member) {
        return MemberMessage.create(
                member.getId(), member.getProvider().getProviderName(), member.getName(),
                member.getEmail(), member.getPhone(), member.getAddress(),
                member.getAgeGroup(), member.getGrade()
        );
    }
}
