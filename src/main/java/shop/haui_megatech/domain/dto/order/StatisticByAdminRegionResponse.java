package shop.haui_megatech.domain.dto.order;

public record StatisticByAdminRegionResponse(
        String adminRegionName,
        Double prices
) {
    public StatisticByAdminRegionResponse(String adminRegionName, Double prices){
        this.adminRegionName = adminRegionName;
        this.prices = prices;
    }
}
