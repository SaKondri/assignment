package saeed.amini.assignment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saeed.amini.assignment.dto.ProductDto;
import saeed.amini.assignment.service.ProductService;

import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping("/domain/springresttest")
@RequiredArgsConstructor
@Log4j2
public class ProductController {

	private final CacheManager cacheManager;

	private final ProductService productService;

	@GetMapping({ "/prid/{id}" })
	public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long id) {
		log.info("fetch product with id "+id);
		return ResponseEntity.ok(productService.getProduct(id));
	}

	@GetMapping({"/prids"})
	public ResponseEntity<List<ProductDto>> getProducts() {
		cacheManager.getCache("products");
		List<ProductDto> result = productService.getProducts();
		log.info("fetch all products: "+result.toString());
		return ResponseEntity.ok(result);
	}

	@PostMapping("/addProduct")
	@ResponseStatus(HttpStatus.CREATED)
	public void addProduct(@RequestBody ProductDto productDto){
		log.info("add product: "+productDto.toString());
		productService.save(productDto);
	}

	@PutMapping("/editProduct")
	@ResponseStatus(HttpStatus.CREATED)
	public void editProduct(@RequestBody ProductDto productDto){
		log.info("edit product: "+productDto.toString());
		productService.save(productDto);
	}
	@DeleteMapping("/deleteProduct/{id}")
	public void deleteProduct(@PathVariable("id") Long id){
		log.info("deleteProduct with id : "+id);
		productService.delete(id);
	}
}
