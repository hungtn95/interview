package com.payit.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.payit.ApplicationMain;
import com.payit.api.BlogComment;
import com.payit.api.BlogPost;
import com.payit.fixture.GenerateObjects;

import io.dropwizard.Configuration;
import io.dropwizard.testing.junit.DropwizardAppRule;

import org.junit.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;

public class BlogPostEndpointTest {
    protected static final String BASE_URL = "/posts";

    @ClassRule
    public static final DropwizardAppRule<Configuration> RULE = new DropwizardAppRule<>(ApplicationMain.class, resourceFilePath("app.yml"));

    protected Client client;
    protected String serverAddress;

    @Before
    public void setUp() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        client = ClientBuilder.newClient();
        serverAddress = "http://localhost:" + RULE.getLocalPort();
    }

    @After
    public void cleanUp() throws JsonProcessingException {
    	getAllPosts().stream().parallel().forEach(post -> deleteABlogPost(post.getId()));
    }

    // Test Blog post feature
    @Test
    public void postABlogPostTest() {
        WebTarget target = client.target(serverAddress).path(BASE_URL);

        BlogPost post = target.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(GenerateObjects.generateBlogPost(), MediaType.APPLICATION_JSON_TYPE), BlogPost.class);

        Assert.assertNotNull(post);
    }

    @Test
    public void getABlogPost(){
        BlogPost post = storeABlogPost();
        WebTarget target = client.target(serverAddress).path(BASE_URL + "/" + post.getId());

        BlogPost foundPost = target.request(MediaType.APPLICATION_JSON_TYPE).get(BlogPost.class);

        Assert.assertNotNull(foundPost);
        Assert.assertEquals(post.getId(), foundPost.getId());
    }

    @Test
    public void testGetAllBlogPosts() throws JsonProcessingException {
        int expected = 100;
        IntStream.range(0, expected).forEach(item -> storeABlogPost());

        Assert.assertEquals(expected, getAllPosts().size());
    }

    public void deleteABlogPost(String id){
        WebTarget target = client.target(serverAddress).path(BASE_URL + "/" + id);
        target.request(MediaType.APPLICATION_JSON_TYPE).delete();
        deleteAllBlogCommentsByPostId(id);
    }

    public BlogPost storeABlogPost(){
        WebTarget target = client.target(serverAddress).path(BASE_URL);

        return target.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(GenerateObjects.generateBlogPost(), MediaType.APPLICATION_JSON_TYPE), BlogPost.class);
    }

    public List<BlogPost> getAllPosts() throws JsonProcessingException {
        WebTarget target = client.target(serverAddress).path(BASE_URL);
        List<BlogPost> allPosts = target.request(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<BlogPost>>() {
                });

        return allPosts;
    }
    
    // Test Blog comment feature
    @Test
    public void postABlogCommentTest() {
        WebTarget target = client.target(serverAddress).path(BASE_URL + "/1/comments");

        BlogComment comment = target.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(GenerateObjects.generateBlogComment("1"), MediaType.APPLICATION_JSON_TYPE), BlogComment.class);

        Assert.assertNotNull(comment);
    }

    @Test
    public void getABlogComment(){
    	BlogComment comment = storeABlogComment("1");
        WebTarget target = client.target(serverAddress).path(BASE_URL + "/" + comment.getPostId() + "/comments/" + comment.getId());

        BlogComment foundComment = target.request(MediaType.APPLICATION_JSON_TYPE).get(BlogComment.class);

        Assert.assertNotNull(foundComment);
        Assert.assertEquals(comment.getId(), foundComment.getId());
    }

    @Test
    public void testGetAllBlogComments() throws JsonProcessingException {
        int expected = 100;
        IntStream.range(0, expected).forEach(item -> storeABlogComment(UUID.randomUUID().toString()));
        Assert.assertEquals(expected, getAllComments().size());
    }
    
    @Test
    public void testGetAllBlogCommentsByPostId() throws JsonProcessingException {
        int expected = 100;
        String postID = UUID.randomUUID().toString();
        IntStream.range(0, expected).forEach(item -> storeABlogComment(postID));
        Assert.assertEquals(expected, getAllCommentsByPostId(postID).size());
    }

    public void deleteABlogComment(String postID, String id){
        WebTarget target = client.target(serverAddress).path(BASE_URL + "/" + postID + "/comments/" + id);
        target.request(MediaType.APPLICATION_JSON_TYPE).delete();
    }
    
    public void deleteAllBlogCommentsByPostId(String postID){
        WebTarget target = client.target(serverAddress).path(BASE_URL + "/" + postID + "/comments/");
        target.request(MediaType.APPLICATION_JSON_TYPE).delete();
    }

    public BlogComment storeABlogComment(String postID){
        WebTarget target = client.target(serverAddress).path(BASE_URL+"/" + postID + "/comments");

        return target.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(GenerateObjects.generateBlogComment(postID), MediaType.APPLICATION_JSON_TYPE), BlogComment.class);
    }

    public List<BlogComment> getAllComments() throws JsonProcessingException {
        WebTarget target = client.target(serverAddress).path(BASE_URL + "/comments");
        List<BlogComment> allComments = target.request(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<BlogComment>>() {
                });

        return allComments;
    }
    
    public List<BlogComment> getAllCommentsByPostId(String postID) throws JsonProcessingException {
        WebTarget target = client.target(serverAddress).path(BASE_URL + "/" + postID + "/comments");
        List<BlogComment> allComments = target.request(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<BlogComment>>() {
                });

        return allComments;
    }
}
