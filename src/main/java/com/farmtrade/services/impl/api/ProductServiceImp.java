package com.farmtrade.services.impl.api;

import com.farmtrade.dto.product.CreateProductDto;
import com.farmtrade.dto.product.UpdateProductDto;
import com.farmtrade.entities.Product;
import com.farmtrade.entities.ProductName;
import com.farmtrade.entities.User;
import com.farmtrade.entities.enums.Role;
import com.farmtrade.exceptions.BadRequestException;
import com.farmtrade.exceptions.ForbiddenException;
import com.farmtrade.repositories.ProductRepository;
import com.farmtrade.services.abstracts.BaseCrudService;
import com.farmtrade.services.api.ProductService;
import com.farmtrade.services.upload.FileStorageService;
import com.farmtrade.services.upload.FileStorageServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Service
public class ProductServiceImp extends BaseCrudService<Product, Long, UpdateProductDto> implements ProductService {
    private final ProductNameServiceImpl productNameService;
    private final FileStorageService fileStorageService;

    public ProductServiceImp(ProductRepository productRepository, ProductNameServiceImpl productNameService, FileStorageService fileStorageService) {
        super(productRepository);
        this.productNameService = productNameService;
        this.fileStorageService = fileStorageService;
    }

    @Override
    protected Class<Product> getClassInstance() {
        return Product.class;
    }

    @Override
    public Product create(CreateProductDto entity, User user) {
        Role userRole = user.getRole();
        if (!userRole.equals(Role.FARMER)) {
            throw new ForbiddenException(String.format("User with role %s cannot perform this cation", userRole));
        }

        ProductName productName = productNameService.findOne(entity.getProductName());

        if (!productName.isApproved()) {
            throw new BadRequestException(String.format(
                    "Cannot create product due to %s is not active.",
                    productName.getName()
            ));
        }

        Product product = Product.builder()
                .owner(user)
                .quantity(entity.getQuantity())
                .size(entity.getSize())
                .productName(productName)
                .reservedQuantity(BigDecimal.ZERO)
                .build();
        return repository.save(product);
    }

    @Override
    public Product updateImage(Long id, MultipartFile img) {
        Product product = findOne(id);

        String existingImgName = product.getImg();
        if (existingImgName != null) {
            this.fileStorageService.removeFile(existingImgName);
        }

        String imageName = fileStorageService.storeImage(img, "product_" + id);
        product.setImg(imageName);

        return repository.save(product);
    }

    @Override
    public void delete(Long id) {
        deleteFileById(id);
        super.delete(id);
    }

    private void deleteFileById(Long id) {
        String fileName = findOne(id).getImg();
        fileStorageService.removeFile(fileName);
    }
}
