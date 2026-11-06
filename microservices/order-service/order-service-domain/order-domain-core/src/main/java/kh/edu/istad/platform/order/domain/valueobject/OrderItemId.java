package kh.edu.istad.platform.order.domain.valueobject;

import java.util.UUID;

public record OrderItemId(
        Integer value // change from UUID to Integer
) {
}
