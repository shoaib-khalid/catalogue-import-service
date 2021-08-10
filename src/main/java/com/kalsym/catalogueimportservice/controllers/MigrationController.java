package com.kalsym.catalogueimportservice.controllers;

import com.kalsym.catalogueimportservice.models.product.Product;
import com.kalsym.catalogueimportservice.services.MigrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class MigrationController {

    @Autowired
    MigrationService migrationService;

    @PostMapping(path = {"/{storeId}/{type}/upload"})
    public ResponseEntity<?> uploadFile(HttpServletRequest request,
                                        @PathVariable String type,
                                        @PathVariable String storeId,
                                        @RequestParam("file") MultipartFile file){

        List<Product> products = new ArrayList<>();
        if(type.equals("daraz")){
            return migrationService.importProductData(migrationService.readProductData(file,';'),';', storeId);
        }
        else if (type.equals("lazada")){
           return migrationService.importProductData(migrationService.readProductData(file,','),',', storeId);
        }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD REQUEST");
    }

}
