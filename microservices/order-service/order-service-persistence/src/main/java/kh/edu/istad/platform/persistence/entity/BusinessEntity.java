package kh.edu.istad.platform.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "businesses")
public class BusinessEntity {

    @Id
    @Embedded.Nullable
    private BusinessEntityId businessEntityId;
//    private UUID businessId;
//    private UUID productId;

    private Boolean bussinessActive;
    private String productName;
    private BigDecimal productPrice;
}
