package com.hexaware.hotpot.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
/*
 * 
 * auth: Varshinee
 * DTO class for validation.
 */
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
public class MenuDto {
	private int menuId;
	@NotEmpty(message="ItemName cannot be empty")
	private String itemName;
	@NotBlank
	private String description;
	@NotNull(message="Price cannot be null")
	@Min(value=1)
	private double price;
	@NotNull(message="Specify type(Veg/Non-Veg)")
	@Pattern(regexp="^(veg|non-veg)$")
	private String type;
	private String image;
	@NotBlank
	private String info;
	private int restaurantId;
	private int categoryId;
    public MenuDto() {}

    // Getters and Setters
    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

}
