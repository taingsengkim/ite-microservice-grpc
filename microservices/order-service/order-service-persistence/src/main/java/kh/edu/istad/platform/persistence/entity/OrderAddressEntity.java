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
@Table(name = "order_addresses")
public class OrderAddressEntity {
    @Id
    private String id;

    private String city;
    private String postalCode;
    private String street;
}
