package com.telusko.simpleWebApplication.service;

import com.telusko.simpleWebApplication.model.Product;
import com.telusko.simpleWebApplication.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

//    List<Product> products = new ArrayList<>( Arrays.asList(
//            new Product(1, "Laptop", 10000),
//            new Product(2, "Mobile", 5000),
//            new Product(3, "Tablet", 3000)));

    public List<Product> getProducts() {

        return productRepo.findAll();
    }

    public Product getProductById(int productId) {
        return productRepo.findById(productId).orElse(new Product());
//        return products.stream().filter(product -> product.getProductId() == productId).
//                findFirst().orElse(new Product(0, "No Item", 0));
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return productRepo.save(product);
//        products.add(product);
    }

    public Product updateProduct(int id,Product product,MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return productRepo.save(product);

//        int index=0;
//        for(int i=0; i<products.size();i++)
//            if(products.get(i).getProductId()==product.getProductId())
//            index=i;
//            products.set(index,product);

    }

    public void deleteProduct(int id) {
        productRepo.deleteById(id);
//        int index=0;
//        for(int i=0; i<products.size();i++)
//            if(products.get(i).getProductId() == productId)
//            index = i;
//            products.remove(index);

    }

    public List<Product> searchProducts(String keyword) {
        return productRepo.searchProducts(keyword);
    }
}
