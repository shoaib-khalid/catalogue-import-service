package com.kalsym.catalogueimportservice.models.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String name;

    private String description;

    private String storeId;

    @Column(name = "categoryId")
    private String categoryId;

    private String status;

    private String thumbnailUrl;

    private String vendor;

    private String region;
    
    private String seoUrl;
    
    private String seoName;
    


    public void update(Product product) {
        if (null != product.getName()) {
            name = product.getName();
        }

        if (null != product.getCategoryId()) {
            categoryId = product.getCategoryId();
        }
        
        if(null!=product.getDescription()){
            description = product.getDescription();
        }
        
        if(null!=product.getStatus()){
            status = product.getStatus();
        }
        if(null!=product.getThumbnailUrl()){
            thumbnailUrl = product.getThumbnailUrl();
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
        final Product other = (Product) obj;
        return Objects.equals(this.id, other.getId());
    }

}
