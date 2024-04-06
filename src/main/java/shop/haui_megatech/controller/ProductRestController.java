package shop.haui_megatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import shop.haui_megatech.base.ResponseUtil;
import shop.haui_megatech.base.RestApiV1;
import shop.haui_megatech.constant.UrlConstant;
import shop.haui_megatech.domain.transfer.ProductDTO;
import shop.haui_megatech.domain.transfer.pagination.PaginationRequestDTO;
import shop.haui_megatech.service.ProductService;

@RestApiV1
public class ProductRestController {
	@Autowired
	private ProductService productService;
	
	@GetMapping(UrlConstant.Product.GET_PRODUCT)
	public ResponseEntity<?> getProductById(@PathVariable(name = "productId", required = true) Integer productId) {
		return ResponseUtil.success(
				productService.getProductById(productId)
			);
	}
	
	@GetMapping(UrlConstant.Product.GET_PRODUCTS)
	public ResponseEntity<?> getProducts(PaginationRequestDTO request) {
		return ResponseUtil.success(productService.getProducts(request));
	}
	
	@PostMapping(UrlConstant.Product.CREATE_PRODUCT)
	public ResponseEntity<?> createProduct(@RequestBody ProductDTO dto) {
		return ResponseUtil.success(productService.createProduct(dto));
	}
	
	@PostMapping(UrlConstant.Product.UPDATE_PRODUCT)
	public ResponseEntity<?> updateProduct(
			@PathVariable(name = "productId", required = true) Integer productId, 
			@RequestBody ProductDTO dto) {
		return ResponseUtil.success(productService.updateProduct(productId, dto));
	}
	
	@DeleteMapping(UrlConstant.Product.DELETE_PRODUCT)
	public ResponseEntity<?> deleteProduct(@PathVariable(name = "productId", required = true) Integer productId) {
		return ResponseUtil.success(productService.deleteProductById(productId));
	}
}
