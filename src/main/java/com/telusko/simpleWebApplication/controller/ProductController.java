package com.telusko.simpleWebApplication.controller;

import com.telusko.simpleWebApplication.model.Product;
import com.telusko.simpleWebApplication.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping ("/products")
    public ResponseEntity<List<Product>>  getProducts() {
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK) ;
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable int productId) {

       Product product = productService.getProductById(productId);
       if(product != null)
           return new ResponseEntity<>(product, HttpStatus.OK);
       else
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping ("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile) {
        try{
            Product product1 = productService.addProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("/product/{productId}/image")
    public ResponseEntity<?> getImageByProductId(@PathVariable int productId) {

            Product product1 = productService.getProductById(productId);
            byte[] imageFile = product1.getImageData();
            return ResponseEntity.ok().contentType(MediaType.valueOf(product1.getImageType()))
                    .body(imageFile);

    }
    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id,@RequestPart Product product,
                                                @RequestPart MultipartFile imageFile) {
        System.out.println(product);
        Product product1= null;
        try {
            product1 = productService.updateProduct(id,product,imageFile);
        } catch (IOException e) {
            return new ResponseEntity<>("Product not found", HttpStatus.BAD_REQUEST);
        }
        if(product1 != null)
            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("Product not found", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product product= productService.getProductById(id);
        if(product != null) {
            productService.deleteProduct(id);
            return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        }
    @GetMapping("/products/search")
    public ResponseEntity<List<Product>>searchProducts(@RequestParam String keyword) {
        List<Product> products = productService.searchProducts(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
