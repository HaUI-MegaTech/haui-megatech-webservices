package shop.haui_megatech.domain.dto.pagination;

import org.springframework.web.bind.annotation.RequestParam;
import shop.haui_megatech.constant.PaginationConstant;

import java.util.Objects;

public record PaginationRequestDTO(
        @RequestParam(name = "keyword", required = false) String keyword,

        @RequestParam(name = "index", required = false) Integer index,

        @RequestParam(name = "direction", required = false) String direction,

        @RequestParam(name = "limit", required = false) Short limit,

        @RequestParam(name = "fields", required = false) String... fields
) {
    public Integer index() {
        return Objects.requireNonNullElse(this.index, PaginationConstant.DEFAULT_PAGE_INDEX);
    }

    public String direction() {
        return Objects.requireNonNullElse(this.direction, PaginationConstant.DEFAULT_ORDER);
    }

    public String[] fields() {
        return Objects.requireNonNullElse(this.fields, PaginationConstant.DEFAULT_ORDER_BY);
    }

    public Short limit() {
        return Objects.requireNonNullElse(this.limit, PaginationConstant.DEFAULT_PAGE_SIZE);
    }
}
