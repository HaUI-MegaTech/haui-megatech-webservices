package shop.haui_megatech.domain.dto.pagination;

import org.springframework.web.bind.annotation.RequestParam;
import shop.haui_megatech.constant.PaginationConstant;

import java.util.Objects;

public record PaginationRequestDTO(
        @RequestParam(name = "keyword", required = false) String keyword,
        @RequestParam(name = "pageIndex", required = false) Integer pageIndex,
        @RequestParam(name = "order", required = false) String order,
        @RequestParam(name = "orderBy", required = false) String orderBy,
        @RequestParam(name = "pageSize", required = false) Short pageSize
) {
    @Override
    public Integer pageIndex() {
        return Objects.requireNonNullElse(this.pageIndex, PaginationConstant.DEFAULT_PAGE_INDEX);
    }

    @Override
    public String order() {
        return Objects.requireNonNullElse(this.order, PaginationConstant.DEFAULT_ORDER);
    }

    @Override
    public String orderBy() {
        return Objects.requireNonNullElse(this.orderBy, PaginationConstant.DEFAULT_ORDER_BY);
    }

    @Override
    public Short pageSize() {
        return Objects.requireNonNullElse(this.pageSize, PaginationConstant.DEFAULT_PAGE_SIZE);
    }
}
