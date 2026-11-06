package kh.edu.istad.platform.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.rmi.server.UID;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItemEntity {

    @Id
    @Embedded.Nullable
    private OrderItemEntityId orderItemEntityId;
    // private Integer id;
    // private UUID orderId;

    private UUID productId;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subTotal;

}
