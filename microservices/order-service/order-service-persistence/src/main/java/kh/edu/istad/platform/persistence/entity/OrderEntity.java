package kh.edu.istad.platform.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class OrderEntity {
    @Id
    private String id;

    private UUID businessId;
    private UUID customerId;
    private String failureMessage;
    private String orderStatus;
    private BigDecimal price;
    private UUID trackingId;
    private UUID orderAddressId;
}
