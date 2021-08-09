package com.kalsym.catalogueimportservice.models.product;

import com.kalsym.catalogueimportservice.models.product.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author 7cu
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "product")
@NoArgsConstructor
public class ProductWithDetails implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String name;


    private String description;

    private String storeId;

    @Column(name = "categoryId")
    private String categoryId;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String thumbnailUrl;

    private String vendor;


    private String region;

    private String seoUrl;
    
    private String seoName;


    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private List<ProductInventory> productInventories;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private List<ProductAsset> productAssets;

    public void update(ProductWithDetails product) {
        if (null != product.getName()) {
            name = product.getName();
        }



        if (null != product.getCategoryId()) {
            categoryId = product.getCategoryId();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductWithDetails other = (ProductWithDetails) obj;
        return Objects.equals(this.id, other.getId());
    }

}
