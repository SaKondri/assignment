package saeed.amini.assignment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import saeed.amini.assignment.dto.ProductDto;
import saeed.amini.assignment.entity.Product;
import saeed.amini.assignment.repository.ProductRepository;


import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @CacheEvict(value="products", allEntries=true)
    public Product save(ProductDto productDto){
        return productRepository.save(dtoToEntity(productDto));
    }
    @Cacheable("products")
    public ProductDto getProduct(Long id){
        Product product= productRepository.findById(id).get();
        return entityToDto(product);
    }
    @Cacheable("products")
    public List<ProductDto> getProducts(){
        List<Product> products= (List<Product>) productRepository.findAll();
        List result = products.stream().map(this::entityToDto).collect(Collectors.toList());
        return result;
    }
    @CacheEvict(value="products", allEntries=true)
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public ProductDto entityToDto(Product product){
        return ProductDto.builder().prid(product.getPrid()).prdname(product.getPrdname()).build();
    }
    public Product dtoToEntity(ProductDto productDto){
       return Product.builder().prid(productDto.getPrid()).prdname(productDto.getPrdname()).build();
    }

}
