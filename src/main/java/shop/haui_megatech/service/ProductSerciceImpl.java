package shop.haui_megatech.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import shop.haui_megatech.constant.ErrorMessageConstant;
import shop.haui_megatech.constant.SuccessMessageConstant;
import shop.haui_megatech.domain.entity.Product;
import shop.haui_megatech.domain.mapper.ProductMapper;
import shop.haui_megatech.domain.transfer.ProductDTO;
import shop.haui_megatech.domain.transfer.common.CommonResponseDTO;
import shop.haui_megatech.domain.transfer.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.transfer.pagination.PaginationResponseDTO;
import shop.haui_megatech.repository.ProductRepository;

@Service
public class ProductSerciceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductMapper mapper;
	
	@Override
	public ProductDTO getProductById(Integer productId) {
		return mapper.toProductDTO(productRepository.findById(productId).get());
	}

	@Override
	public ProductDTO createProduct(ProductDTO dto) {
		return mapper.toProductDTO(productRepository.save(Product
					.builder()
					.name(dto.name())
					.price(dto.price())
					.build()
				));
	}

	@Override
	public CommonResponseDTO updateProduct(Integer productId, ProductDTO dto) {
		Optional<Product> found = productRepository.findById(productId);
		if (found.isEmpty()) 
			return CommonResponseDTO
					.builder()
					.result(false)
					.message(ErrorMessageConstant.Product.NOT_FOUND)
					.build();
		
		Product foundProduct = found.get();
		foundProduct.setName(dto.name());
		foundProduct.setPrice(dto.price());
		productRepository.save(foundProduct);
		return CommonResponseDTO
				.builder()
				.result(true)
				.message(SuccessMessageConstant.Product.UPDATED)
				.build();
	}

	@Override
	public CommonResponseDTO deleteProductById(Integer id) {
		Optional<Product> found = productRepository.findById(id);
		if (found.isEmpty()) 
			return CommonResponseDTO
					.builder()
					.result(false)
					.message(ErrorMessageConstant.Product.NOT_FOUND)
					.build();
		
		productRepository.deleteById(id);
		return CommonResponseDTO
				.builder()
				.result(true)
				.message(SuccessMessageConstant.Product.DELETED)
				.build();
	}

	@Override
	public PaginationResponseDTO<ProductDTO> getProducts(PaginationRequestDTO request) {
		Sort sort = request.order().equals("asc")
				? Sort.by(request.orderBy()).ascending()
				: Sort.by(request.orderBy()).descending();
		Pageable pageable = PageRequest.of(request.pageIndex(), request.pageSize(), sort);
		Page<Product> page = productRepository.findAll(pageable);
		List<Product> products = page.getContent();
		
		return PaginationResponseDTO.<ProductDTO>builder()
				.pageIndex(request.pageIndex())
				.pageSize(request.pageSize())
				.totalItems(page.getTotalElements())
				.totalPages(page.getTotalPages())
				.data(products.parallelStream().map(mapper::toProductDTO).collect(Collectors.toList()))
				.build();
	}

}
