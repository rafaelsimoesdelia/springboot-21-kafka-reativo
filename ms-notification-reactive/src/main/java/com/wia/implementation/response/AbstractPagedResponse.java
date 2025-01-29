package com.wia.implementation.response;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public abstract class AbstractPagedResponse<T> {

    @JsonProperty("content")
    private List<T> content;

    @JsonIgnore
    private Page<T> page;

    @JsonProperty("page")
    private Integer pageNumber;

    @JsonProperty("total-pages")
    private Integer totalPages;

    @JsonProperty("total-elements")
    private Integer totalElements;

    protected AbstractPagedResponse(Page<T> page) {
	this.page = page;
	if (Objects.nonNull(page)) {
	    this.content = page.getContent();
	    this.pageNumber = page.getNumber();
	    this.totalPages = page.getTotalPages();
	    this.totalElements = Integer.valueOf(Long.toString(page.getTotalElements()));
	}
    }

    protected AbstractPagedResponse(List<T> content) {
	this.content = content;
	if (Objects.nonNull(content)) {
	    this.page = new PageImpl<>(content);
	    this.pageNumber = 1;
	    this.totalPages = 1;
	    this.totalElements = content.size();
	}
    }
}