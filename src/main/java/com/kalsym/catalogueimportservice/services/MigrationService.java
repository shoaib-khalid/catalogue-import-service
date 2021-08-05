package com.kalsym.catalogueimportservice.services;

import com.kalsym.catalogueimportservice.models.product.Product;
import com.kalsym.catalogueimportservice.repositories.MigrationRepository;
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
    MigrationRepository migrationRepository;


    public List<List<String>> readProductData(MultipartFile file, char delimiter)
    {
        String filename = file.getOriginalFilename();
        System.out.println(filename);
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

    public ResponseEntity importProductData(List<List<String>> productData, char delimiter){
        List<Product> importedProducts = new ArrayList<>();
        List<String> columns = productData.get(0);
        System.out.println(columns);
        productData.remove(0);
        System.out.println(productData.size());
        try{
            for(List<String> product : productData)
            {
                System.out.println(product.size()+" data : "+ product);
                Product p = new Product();
                switch (delimiter){
                    case ';':{
                        p.setName(product.get(columns.indexOf("name")));
                        p.setDescription(product.get(columns.indexOf("short_description"))+"<br>"+
                                product.get(columns.indexOf("description"))+"<br>");
                        p.setStatus(product.get(columns.indexOf("Status")));
                        p.setThumbnailUrl(product.get(columns.indexOf("product_picture1")));
                        importedProducts.add(p);
                        break;
                    }
                    case ',':{
                        p.setName(product.get(columns.indexOf("*Product Name")));
                        p.setDescription(product.get(columns.indexOf("Short Description"))+"<br>");
                        p.setStatus("");
                        p.setThumbnailUrl(product.get(columns.indexOf("*Product Images1")));
                        importedProducts.add(p);
                        break;
                    }
                }
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong Vendor Selected !");
        }
        return ResponseEntity.status(HttpStatus.OK).body(importedProducts);
    }

    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(";");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

}
