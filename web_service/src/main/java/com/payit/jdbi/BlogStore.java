package com.payit.jdbi;

import com.payit.api.BlogPost;
import com.payit.api.BlogComment;
import java.util.List;

public interface BlogStore {

    public List<BlogPost> getAllPosts();
    public BlogPost storeBlogPost(BlogPost blogpost);
    public BlogPost getBlogPostById(String id);
    public void updateBlogPost(String id, BlogPost blogPost);
    public void deleteBlogPost(String id);
    
    public List<BlogComment> getAllComments();
    public List<BlogComment> getAllCommentsByPostId(String postId);
    public BlogComment storeBlogComment(BlogComment blogComment);
    public BlogComment getBlogCommentById(String id);
    public void updateBlogComment(String id, BlogComment blogComment);
    public void deleteBlogComment(String id);
    public void deleteAllCommentByPostId(String postId);
}
