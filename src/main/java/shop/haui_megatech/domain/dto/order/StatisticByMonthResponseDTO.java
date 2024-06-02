package shop.haui_megatech.domain.dto.order;

import lombok.Builder;

public record StatisticByMonthResponseDTO(
        String dates,
        Double prices
) {
    public StatisticByMonthResponseDTO(Object dates, Double prices) {
        this(dates.toString(), prices);
    }
}
