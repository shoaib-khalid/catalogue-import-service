package com.kalsym.catalogueimportservice.models.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author 7cu
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "product_inventory")
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductInventoryWithDetails implements Serializable {

    @Id
    private String itemCode;

    private Double price;
    private Double compareAtprice;

    private String SKU;

    //private String name;
    private Integer quantity;

    private String productId;
    
    private String status;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "itemCode")
    private List<ProductInventoryItem> productInventoryItems;

}
