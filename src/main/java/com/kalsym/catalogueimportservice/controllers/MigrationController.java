package com.kalsym.catalogueimportservice.controllers;

import com.kalsym.catalogueimportservice.services.MigrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.kalsym.catalogueimportservice.utils.LogUtil;

import javax.servlet.http.HttpServletRequest;

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
        String logprefix = request.getRequestURI() + " ";
        String location = Thread.currentThread().getStackTrace()[1].getMethodName();
        LogUtil.info(logprefix, location, "", "");

        if(type.equals("daraz")){
            LogUtil.info(logprefix, location, "Daraz catalogue detected !", "");
            return migrationService.importProductData(migrationService.readProductData(file,';'),';', storeId);
        }
        else if (type.equals("lazada")){
            LogUtil.info(logprefix, location, "Lazada catalogue detected !", "");
           return migrationService.importProductData(migrationService.readProductData(file,','),',', storeId);
        }
        else{
            LogUtil.info(logprefix, location, "Invalid service provider selected !", "");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD REQUEST");
        }

    }

}
