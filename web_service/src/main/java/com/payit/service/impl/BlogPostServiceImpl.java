package com.payit.service.impl;

import com.payit.api.BlogComment;
import com.payit.api.BlogPost;
import com.payit.jdbi.BlogStore;
import com.payit.service.BlogPostService;

import javax.inject.Inject;

import java.util.List;

public class BlogPostServiceImpl implements BlogPostService {

    private final BlogStore blogStore;

    @Inject
    public BlogPostServiceImpl(BlogStore blogStore){
        this.blogStore = blogStore;
    }


    @Override
    public List<BlogPost> getAllPosts() {
        return blogStore.getAllPosts();
    }

    @Override
    public BlogPost getPostById(String id) {
        return blogStore.getBlogPostById(id);
    }

    @Override
    public void deletePost(String id) {
        blogStore.deleteBlogPost(id);
    }

    @Override
    public BlogPost createPost(BlogPost post) {
        return blogStore.storeBlogPost(post);
    }

    @Override
    public void updatePost(String id, BlogPost blogPost) {
        blogStore.updateBlogPost(id, blogPost);
    }
    
    @Override
    public List<BlogComment> getAllComments() {
        return blogStore.getAllComments();
    }
    
    @Override
    public List<BlogComment> getAllCommentsByPostId(String postId) {
        return blogStore.getAllCommentsByPostId(postId);
    }

    @Override
    public BlogComment getCommentById(String id) {
        return blogStore.getBlogCommentById(id);
    }

    @Override
    public void deleteComment(String id) {
        blogStore.deleteBlogComment(id);
    }

    @Override
    public void deleteAllCommentsByPostId(String postId) {
        blogStore.deleteAllCommentByPostId(postId);
    }
    
    @Override
    public BlogComment createComment(BlogComment comment) {
        return blogStore.storeBlogComment(comment);
    }

    @Override
    public void updateComment(String id, BlogComment blogComment) {
        blogStore.updateBlogComment(id, blogComment);
    }
}
