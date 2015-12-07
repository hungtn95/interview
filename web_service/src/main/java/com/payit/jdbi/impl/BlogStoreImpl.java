package com.payit.jdbi.impl;

import com.payit.api.BlogComment;
import com.payit.api.BlogPost;
import com.payit.jdbi.BlogStore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Richard on 8/5/15.
 */
public class BlogStoreImpl implements BlogStore{
    private static final Map<String, BlogPost> inMemoryStore = new HashMap<>();
    private static final Map<String, BlogComment> inMemoryStore2 = new HashMap<>();

    @Override
    public List<BlogPost> getAllPosts() {
        return inMemoryStore.values().stream().collect(Collectors.toList());
    }

    @Override
    public BlogPost storeBlogPost(BlogPost blogpost) {
        inMemoryStore.put(blogpost.getId(), blogpost);
        return blogpost;
    }

    @Override
    public BlogPost getBlogPostById(String id) {
        return inMemoryStore.getOrDefault(id, null);
    }

    @Override
    public void updateBlogPost(String id, BlogPost blogPost) {
        inMemoryStore.replace(id, blogPost);
    }

    @Override
    public void deleteBlogPost(String id) {
        inMemoryStore.remove(id);
    }
    
    @Override
    public List<BlogComment> getAllComments() {
        return inMemoryStore2.values().stream().collect(Collectors.toList());
    }
    
    @Override
    public List<BlogComment> getAllCommentsByPostId(String postId) {
        return inMemoryStore2.values().stream().filter(c -> c.getPostId().equals(postId)).collect(Collectors.toList());
    }
    
    @Override
    public BlogComment storeBlogComment(BlogComment blogComment) {
        inMemoryStore2.put(blogComment.getId(), blogComment);
        return blogComment;
    }

    @Override
    public BlogComment getBlogCommentById(String id) {
        return inMemoryStore2.getOrDefault(id, null);
    }

    @Override
    public void updateBlogComment(String id, BlogComment blogComment) {
        inMemoryStore2.replace(id, blogComment);
    }

    @Override
    public void deleteBlogComment(String id) {
        inMemoryStore2.remove(id);
    }
    
    @Override
    public void deleteAllCommentByPostId(String postId) {
    	for (BlogComment blogComment : inMemoryStore2.values()) {
    		if (blogComment.getPostId().equals(postId)) {
    	        inMemoryStore2.remove(blogComment.getId());
    		}
    	}
    }
}
