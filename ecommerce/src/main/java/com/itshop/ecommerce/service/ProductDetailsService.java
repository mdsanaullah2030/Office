package com.itshop.ecommerce.service;

import com.itshop.ecommerce.entity.*;
import com.itshop.ecommerce.repository.*;
import org.apache.catalina.LifecycleState;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductDetailsService {

    @Autowired
    private ProductDetailsRepository productDetailsRepository;

    @Value("${image.upload.dir}")
    private String uploadDir;

    @Autowired
    private CatagoryRepository catagoryRepository;


    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductItemRepository productItemRepository;

    public List<ProductDetails> getAllProductDetails(){
        return productDetailsRepository.findAll();

    }


    public ProductDetails getProductById(int id) {
        return productDetailsRepository.findById(id).orElse(null);
    }

//Catagory ID By Get All Product Details
    public List<ProductDetails> getProductDetailsByCatagoryId(int catagoryId) {
        return productDetailsRepository.findByCatagoryId(catagoryId);
    }



    //Brand ID By Get All Product Details
    public List<ProductDetails> getProductDetailsByBrandId(int brandId) {
        return productDetailsRepository.findByBrandId(brandId);
    }

//By Product Id ProductDetails
    public List<ProductDetails> getProductDetailsByProductId(int productId) {
        return productDetailsRepository.findByProductId(productId);
    }

    public Optional<ProductDetails> getProductDetailsByName(String name) {
        return productDetailsRepository.findByName(name);
    }





    @Transactional
    public void saveProductDetails(ProductDetails productDetails, MultipartFile image1File, MultipartFile image2File,MultipartFile image3File) throws IOException {

        if (productDetails.getCatagory() != null && productDetails.getCatagory().getId() != 0) {
            Catagory cat = catagoryRepository.findById(productDetails.getCatagory().getId())
                    .orElseThrow(() -> new RuntimeException("Catagory not found"));
            productDetails.setCatagory(cat);
        } else {
            productDetails.setCatagory(null);
        }

        if (productDetails.getProduct() != null && productDetails.getProduct().getId() != 0) {
            Product prod = productRepository.findById(productDetails.getProduct().getId())
                    .orElse(null);  // orThrow if needed
            productDetails.setProduct(prod);
        } else {
            productDetails.setProduct(null);
        }

        if (productDetails.getBrand() != null && productDetails.getBrand().getId() != 0) {
            Brand brand = brandRepository.findById(productDetails.getBrand().getId())
                    .orElse(null);
            productDetails.setBrand(brand);
        } else {
            productDetails.setBrand(null);
        }


        if (image1File != null && !image1File.isEmpty()) {
            String imageFileName = saveImage(image1File, productDetails);
            productDetails.setImagea(imageFileName);
        }

        if (image2File != null && !image2File.isEmpty()) {
            String imageFileName = saveImage(image2File, productDetails);
            productDetails.setImageb(imageFileName);
        }


        if (image3File != null && !image3File.isEmpty()) {
            String imageFileName = saveImage(image3File, productDetails);
            productDetails.setImagec(imageFileName);
        }



        productDetailsRepository.save(productDetails);
    }




    public String saveImage(MultipartFile file, ProductDetails l) throws IOException {
        Path uploadPath = Paths.get(uploadDir + "/location");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        String filename = UUID.randomUUID() + "." + extension;
        Path filePath = uploadPath.resolve(filename);

        Files.copy(file.getInputStream(), filePath);


        return filename;
    }




//Updete
@Transactional
public ProductDetails updateProductDetails(
        int id,
        ProductDetails incoming,                 // <-- the new values
        MultipartFile image1File,
        MultipartFile image2File,
        MultipartFile image3File
) throws IOException {
    // 1) fetch existing
    ProductDetails existing = productDetailsRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));

    // 2) copy scalar fields
    existing.setName(incoming.getName());
    existing.setQuantity(incoming.getQuantity());
    existing.setRegularprice(incoming.getRegularprice());
    existing.setSpecialprice(incoming.getSpecialprice());
    existing.setTitle(incoming.getTitle());
    existing.setDetails(incoming.getDetails());
    existing.setSpecification(incoming.getSpecification());
    existing.setWarranty(incoming.getWarranty());



    if (image1File != null && !image1File.isEmpty()) {
        String filename = saveImage(image1File, existing);
        existing.setImagea(filename);
    }
    if (image2File != null && !image2File.isEmpty()) {
        String filename = saveImage(image2File, existing);
        existing.setImagea(filename);
    }


    if (image3File != null && !image3File.isEmpty()) {
        String filename = saveImage(image3File, existing);
        existing.setImagea(filename);
    }



    // 3) copy relationships
    if (incoming.getCatagory() != null && incoming.getCatagory().getId() != 0) {
        Catagory cat = catagoryRepository.findById(incoming.getCatagory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        existing.setCatagory(cat);
    } else {
        existing.setCatagory(null);
    }

    if (incoming.getProduct() != null && incoming.getProduct().getId() != 0) {
        Product prod = productRepository.findById(incoming.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        existing.setProduct(prod);
    } else {
        existing.setProduct(null);
    }


    if (incoming.getProductItem() != null && incoming.getProductItem().getId() != 0) {
        ProductItem prod = productItemRepository.findById(incoming.getProductItem().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        existing.setProductItem(prod);
    } else {
        existing.setProduct(null);
    }



    if (incoming.getBrand() != null && incoming.getBrand().getId() != 0) {
        Brand br = brandRepository.findById(incoming.getBrand().getId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        existing.setBrand(br);
    } else {
        existing.setBrand(null);
    }


    // 5) persist and return
    return productDetailsRepository.save(existing);
}





//Delete

    public void deleteProductDetils(int id) {
        if (!productDetailsRepository.existsById(id)) {
            throw new RuntimeException("Item not found with ID: " + id);
        }


        // Now safely delete the item
        productDetailsRepository.deleteById(id);
    }




///Filter


    public List<ProductDetails> filterProductDetails(
            String brandname,
            String productName,
            Double regularPrice,
            Integer warranty,
            Integer productItemId) {
        return productDetailsRepository
                .filterByBrandnameAndProductNameAndRegularPriceAndWarrantyAndProductItem(
                        brandname, productName, regularPrice, warranty, productItemId
                );
    }

//
public List<ProductDetails> getPublishedProducts() {
    return productDetailsRepository.findPublishedProducts();
}

//

}


