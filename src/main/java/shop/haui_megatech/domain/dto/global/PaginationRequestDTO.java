package shop.haui_megatech.domain.dto.global;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.RequestParam;
import shop.haui_megatech.constant.PaginationConstant;

import java.util.Objects;

public record PaginationRequestDTO(
        @RequestParam(name = "keyword", required = false)
        @Parameter(description = "Từ khóa cần tìm, có thể tìm theo nhiều từ khóa ngăn cách bởi dấu \",\"")
        String keyword,

        @RequestParam(name = "index", required = false)
        @Parameter(description = "Chứa chỉ số trang cần lấy, bắt đầu từ 0")
        Integer index,

        @RequestParam(name = "direction", required = false)
        @Parameter(description = "Hướng sắp xếp: \"asc\" hoặc \"desc\"")
        String direction,

        @RequestParam(name = "limit", required = false)
        @Parameter(description = "Số bản ghi mong muốn cho 1 trang")
        Short limit,

        @RequestParam(name = "fields", required = false)
        @Parameter(description = "Các tiêu chí sắp xếp. Có thể sắp xếp theo nhiều tiêu chí, ngăn cách nhau bởi dấu \",\"")
        String... fields
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
