package com.payit.service;

import com.payit.api.BlogComment;
import com.payit.api.BlogPost;

import java.util.List;

public interface BlogPostService {
    public List<BlogPost> getAllPosts();

    public BlogPost getPostById(String id);
    
    public void deletePost(String id);

    public BlogPost createPost(BlogPost post);
    
    public void updatePost(String id, BlogPost post);
    
    public List<BlogComment> getAllComments();

    public BlogComment getCommentById(String id);
    
    public List<BlogComment> getAllCommentsByPostId(String postId);
    
    public void deleteComment(String id);
    
    public void deleteAllCommentsByPostId(String postId);

    public BlogComment createComment(BlogComment comment);
    
    public void updateComment(String id, BlogComment comment);
}
