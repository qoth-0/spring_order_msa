package com.encore.ordering.order.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Ordering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.ORDERED; // @Builder.Default를 사용해야 값이 적용됨

    @OneToMany(mappedBy = "ordering", cascade = CascadeType.PERSIST) // 기본값이 lazy
    private List<OrderItem> orderItems = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdTime;
    @UpdateTimestamp
    private LocalDateTime updatedTime;

    @Builder
    public Ordering(Long memberId) {
        this.memberId = memberId;
    }

    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCELED;
    }

}
