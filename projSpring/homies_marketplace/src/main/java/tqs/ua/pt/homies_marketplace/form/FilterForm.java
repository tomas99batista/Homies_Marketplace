package tqs.ua.pt.homies_marketplace.form;

import java.util.List;

public class FilterForm {
    private String city;
    private Integer bedrooms;
    private Integer bathrooms;
    private List<String> features;
    private String type;
    //private Integer minPrice;
    //private Integer maxPrice;

        /*
        public Integer getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(Integer minPrice) {
            this.minPrice = minPrice;
        }

        public Integer getMaxPrice() {
            return maxPrice;
        }

        public void setMaxPrice(Integer maxPrice) {
            this.maxPrice = maxPrice;
        }
        */

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }


}

