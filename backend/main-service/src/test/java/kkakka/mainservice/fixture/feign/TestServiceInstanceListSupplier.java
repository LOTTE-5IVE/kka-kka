package kkakka.mainservice.fixture.feign;

import java.util.ArrayList;
import java.util.List;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Profile;
import reactor.core.publisher.Flux;

@Profile("test")
public class TestServiceInstanceListSupplier implements ServiceInstanceListSupplier {

    private final String serviceId;
    private final int[] ports;

    public TestServiceInstanceListSupplier(String serviceId, int... ports) {
        this.serviceId = serviceId;
        this.ports = ports;
    }

    @Override
    public String getServiceId() {
        return serviceId;
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        final List<ServiceInstance> result = new ArrayList<>();
        for (int i = 0; i < ports.length; i++) {
            result.add(new DefaultServiceInstance(
                    serviceId + i, getServiceId(),
                    "localhost",
                    ports[i],
                    false)
            );
        }
        return Flux.just(result);
    }
}
