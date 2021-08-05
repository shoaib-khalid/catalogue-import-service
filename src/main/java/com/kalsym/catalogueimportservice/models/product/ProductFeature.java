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
@Table(name = "product_feature")
@NoArgsConstructor
public class ProductFeature implements Serializable {

    @Id
    private String id;

    private String name;
    private String imageUrl;

    private String productId;

}
