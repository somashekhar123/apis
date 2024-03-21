package com.blog.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@AllArgsConstructor  // with arguments constrctor
@NoArgsConstructor //default constrction
public class PostDto {
    private long id;
    @NotEmpty
    @Size(min=2,message = "Title must be more than 2 characters")
    private String title;
    @NotEmpty
    @Size(min=4 , message = "Description should be least 4 characters")
    private String Description;
    @NotEmpty
    @Size(min=3,message = "content should be at least 4 characters")
    private String content;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



}
