package com.kalsym.catalogueimportservice.services;

import com.kalsym.catalogueimportservice.models.product.*;
import com.kalsym.catalogueimportservice.models.product.enums.Status;
import com.kalsym.catalogueimportservice.repositories.ProductInventoryRepository;
import com.kalsym.catalogueimportservice.repositories.ProductRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Service
public class MigrationService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductInventoryRepository productInventoryRepository;


    public List<List<String>> readProductData(MultipartFile file, char delimiter)
    {
        CSVParser parser = new CSVParserBuilder().withSeparator(delimiter).build();
        List<List<String>> records = new ArrayList<List<String>>();
        try (CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(file.getInputStream())).withCSVParser(parser).build()) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    public ResponseEntity importProductData(List<List<String>> productData, char delimiter, String storeId){
        List<ProductWithDetails> importedProducts = new ArrayList<>();
        List<String> columns = productData.get(0);
        productData.remove(0);

        try{
            switch (delimiter){
                case ';':{
                    for(List<String> product : productData)
                    {
                        ProductWithDetails p = new ProductWithDetails();
                        ProductInventory productInventory = new ProductInventory();

                        p.setName(product.get(columns.indexOf("name")));
                        p.setDescription(product.get(columns.indexOf("short_description"))+"<br>");
                        if(product.get(columns.indexOf("Status")).toLowerCase().equals("active")){
                            p.setStatus(Status.ACTIVE);
                        }
                        else{
                            p.setStatus(Status.DRAFT);
                        }

                        p.setStoreId(storeId);
                        p.setThumbnailUrl(product.get(columns.indexOf("MainImage")));
                        p.setProductAssets(null);
                        p.setCategoryId(null);
                        p.setProductInventories(null);

                        productInventory.setProductId(productRepository.save(p).getId());
                        //TODO: SKU CHANGED TO 100 in DATABASE
                        productInventory.setSKU(product.get(0));
                        productInventory.setPrice(Double.parseDouble(product.get(columns.indexOf("price"))));
                        productInventory.setQuantity(Integer.parseInt(product.get(columns.indexOf("quantity"))));
                        productInventory.setCompareAtprice(null);
                        productInventory.setItemCode(productInventory.getProductId()+"-aaa");
                        productInventoryRepository.save(productInventory);




//                        if( productData.size() > (productData.indexOf(product)+1) &&
//                                productData.get(productData.indexOf(product)+1)
//                                .get(0)
//                                .contains(product.get(0)
//                                        .split("-")[0])){
//
//                            ProductVariant productVariant = new ProductVariant();
//                            productVariant.setProduct(p);
//
//                            ProductVariantAvailable variantAvailable = new ProductVariantAvailable();
//                            variantAvailable.setProductId(p.getId());
//                            variantAvailable.setValue("");
//
//                        }

                        importedProducts.add(p);
                    }

                    break;
                }
                case ',':{
                    for(List<String> product : productData)
                    {
                        ProductWithDetails p = new ProductWithDetails();
                        ProductInventory productInventory = new ProductInventory();

                        p.setStoreId(storeId);
                        p.setName(product.get(columns.indexOf("*Product Name")));
//                        p.setCategoryId(product.get(columns.indexOf("catId")));
                        p.setDescription(product.get(columns.indexOf("Short Description"))+"<br>");
                        p.setStatus(Status.DRAFT );
                        p.setThumbnailUrl(product.get(columns.indexOf("*Product Images1")));

                        productInventory.setProductId(productRepository.save(p).getId());
                        //TODO: SKU CHANGED TO 100 in DATABASE
                        productInventory.setSKU(product.get(0));
                        productInventory.setItemCode(productInventory.getProductId()+"-aaa");
                        productInventoryRepository.save(productInventory);

                        importedProducts.add(p);
                    }
                    break;
                }
            }




        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong Vendor Selected !");
        }
        return ResponseEntity.status(HttpStatus.OK).body(importedProducts);
    }


}
