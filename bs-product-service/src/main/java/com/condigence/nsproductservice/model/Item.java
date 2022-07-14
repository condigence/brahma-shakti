package com.condigence.nsproductservice.model;
import org.springframework.data.annotation.Id;

import java.util.Date;


public class Item {

	@Id
	private Long id;


	private String name;


	private String code;


	private int price;


	private Integer mrp;


	private Integer dispPrice;


	private Integer discount;


	private String type;


	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	private Integer capacity;


	private Long brandId;


	private Long imageId;


	private Date dateCreated;


	private Integer quantity;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public Integer getPrice() {
		return price;
	}

	public Integer getMrp() {
		return mrp;
	}

	public Integer getDispPrice() {
		return dispPrice;
	}

	public Integer getDiscount() {
		return discount;
	}

	public String getType() {
		return type;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public Long getImageId() {
		return imageId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public void setMrp(Integer mrp) {
		this.mrp = mrp;
	}

	public void setDispPrice(Integer dispPrice) {
		this.dispPrice = dispPrice;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCapacity(Integer capcity) {
		this.capacity = capcity;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

}
