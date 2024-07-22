package com.blogger.blog.blog_app_apis.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

    public Integer categoryId;

    @NotBlank
    @Size(min = 4, message = "minimum size of category is 4")
    public String categoryTitle;

    @NotBlank
    @Size(min = 10, message = "minimum size of category is 10")
    public String categoryDescription;
}
