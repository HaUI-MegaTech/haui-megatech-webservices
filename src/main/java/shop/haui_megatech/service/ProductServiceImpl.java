package shop.haui_megatech.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import shop.haui_megatech.constant.ErrorMessageConstant;
import shop.haui_megatech.constant.SuccessMessageConstant;
import shop.haui_megatech.domain.dto.common.CommonGetByIdResponseDTO;
import shop.haui_megatech.domain.entity.Product;
import shop.haui_megatech.domain.mapper.ProductMapper;
import shop.haui_megatech.domain.dto.ProductDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationResponseDTO;
import shop.haui_megatech.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductMapper mapper;

	@Autowired
	private MessageSource messageSource;

	@Override
	public CommonGetByIdResponseDTO<ProductDTO> getProductById(Integer productId) {

		Optional<Product> foundProduct = productRepository.findById(productId);

		return foundProduct.isPresent()
				? CommonGetByIdResponseDTO.<ProductDTO>builder()
					.result(true)
					.message(messageSource
							.getMessage(SuccessMessageConstant.Product.FOUND, null, LocaleContextHolder.getLocale()))
					.data(mapper.toProductDTO(foundProduct.get()))
					.build()
				: CommonGetByIdResponseDTO.<ProductDTO>builder()
					.result(false)
					.message(messageSource
							.getMessage(ErrorMessageConstant.Product.NOT_FOUND, null, LocaleContextHolder.getLocale()))
					.data(null)
					.build();
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
				.items(products.parallelStream().map(mapper::toProductDTO).collect(Collectors.toList()))
				.build();
	}

}
