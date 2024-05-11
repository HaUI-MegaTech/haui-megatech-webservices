package shop.haui_megatech.controller;

public class Item {
    private String  link;
    private String  title;
    private PageMap pagemap;
    private String  price;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PageMap getPagemap() {
        return pagemap;
    }

    public void setPagemap(PageMap pagemap) {
        this.pagemap = pagemap;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
