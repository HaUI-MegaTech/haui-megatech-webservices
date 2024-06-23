package shop.haui_megatech.domain.dto.product;

import lombok.Getter;

@Getter
public class UpdateProductRequestDTO {
    private String  name;
    private Float   oldPrice;
    private Float   currentPrice;
    private Integer discountPercent;
    private Integer remaining;
    private String  processor;
    private Integer cores;
    private Integer threads;
    private String  frequency;
    private String  boostFrequency;
    private String  cacheCapacity;
    private String  memoryCapacity;
    private String  memoryType;
    private String  memoryBus;
    private String  maxMemoryCapacity;
    private String  storage;
    private String  displaySize;
    private String  resolution;
    private String  refreshRate;
    private String  colorGamut;
    private String  panelType;
    private String  touchScreen;
    private String  graphicsCard;
    private String  soundTechnology;
    private String  wirelessConnectivity;
    private String  sdCard;
    private String  webcam;
    private String  coolingFan;
    private String  miscFeature;
    private String  backlitKeyboard;
    private String  dimensionWeight;
    private String  material;
    private String  batteryCapacity;
    private String  chargerCapacity;
    private String  os;
    private Integer launchDate;
}
