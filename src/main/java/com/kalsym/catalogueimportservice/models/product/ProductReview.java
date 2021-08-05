package com.kalsym.catalogueimportservice.models.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author 7cu
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "product_review")
@NoArgsConstructor
//Using IdClass annotation to resolve the composite PK problem in hibernate
//@IdClass(ProductReviewId.class)
public class ProductReview implements Serializable {

    @Id
    private String customerId;

    private int rating;

    private String review;

    private String productId;

    //    @Id
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "productId", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
//    private Product product;
//    @Id 
//   
}
