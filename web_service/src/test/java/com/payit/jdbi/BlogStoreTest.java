package com.payit.jdbi;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.payit.ApplicationMain;
import com.payit.ServiceInjector;
import com.payit.api.BlogComment;
import com.payit.api.BlogPost;

import io.dropwizard.Configuration;
import io.dropwizard.testing.junit.DropwizardAppRule;

import org.junit.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static com.payit.fixture.GenerateObjects.generateBlogPost;
import static com.payit.fixture.GenerateObjects.generateBlogComment;
import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;

public class BlogStoreTest {
    protected BlogStore blogStore;

    @ClassRule
    public static final DropwizardAppRule<Configuration> RULE = new DropwizardAppRule<>(ApplicationMain.class, resourceFilePath("app.yml"));

    @Before
    public void setUp(){
        Injector injector = Guice.createInjector(new ServiceInjector());
        blogStore = injector.getInstance(BlogStore.class);
    }

    @After
    public void tearDown(){
        blogStore.getAllPosts().stream().forEach(blog -> blogStore.deleteBlogPost(blog.getId()));
        blogStore.getAllComments().stream().forEach(blog -> blogStore.deleteBlogComment(blog.getId()));
    }

    // Test Blog post feature
    @Test
    public void storeBlogPostTest(){
        BlogPost post = generateBlogPost();
        blogStore.storeBlogPost(post);
        BlogPost storedPost = blogStore.getBlogPostById(post.getId());
        Assert.assertNotNull(storedPost);
    }

    @Test
    public void getBlogPostByIdTest(){
        BlogPost post = generateBlogPost();
        blogStore.storeBlogPost(post);

        BlogPost storedPost = blogStore.getBlogPostById(post.getId());
        Assert.assertNotNull(storedPost);
    }

    @Test
    public void getBlogPostByIdNotFoundTest(){
        BlogPost notFoundPost = blogStore.getBlogPostById(UUID.randomUUID().toString());
        Assert.assertNull(notFoundPost);
    }

    @Test
    public void updateBlogPostTest() throws IllegalAccessException, InvocationTargetException {
        BlogPost post = generateBlogPost();
        blogStore.storeBlogPost(post);

        String newTitle = UUID.randomUUID().toString();       
        post.setTitle(newTitle);
        
        blogStore.updateBlogPost(post.getId(), post);

        BlogPost updatedPost = blogStore.getBlogPostById(post.getId());
        Assert.assertEquals(newTitle, updatedPost.getTitle());
    }

	@Test
    public void getAllPostsTest(){
        int expectedSize = 100;

        IntStream.range(0, expectedSize).parallel().forEach(item -> {
            BlogPost post = generateBlogPost();
            blogStore.storeBlogPost(post);
        });

        List<BlogPost> allBlogPosts = blogStore.getAllPosts();

        Assert.assertEquals(expectedSize, allBlogPosts.size());

    }

    // Test Blog comment feature
    @Test
    public void storeBlogCommentTest(){
        BlogPost post = generateBlogPost();
        blogStore.storeBlogPost(post);
        BlogComment comment = generateBlogComment(post.getId());
        blogStore.storeBlogComment(comment);
        BlogComment storedComment = blogStore.getBlogCommentById(comment.getId());
        Assert.assertNotNull(storedComment);
    }

    @Test
    public void getBlogCommentByIdTest(){
        BlogPost post = generateBlogPost();
        blogStore.storeBlogPost(post);
        BlogComment comment = generateBlogComment(post.getId());
        blogStore.storeBlogComment(comment);
        
        BlogComment storedComment = blogStore.getBlogCommentById(comment.getId());
        Assert.assertNotNull(storedComment);
    }

    @Test
    public void getBlogCommentByIdNotFoundTest(){
        BlogPost notFoundPost = blogStore.getBlogPostById(UUID.randomUUID().toString());
        Assert.assertNull(notFoundPost);
    }

    @Test
    public void updateBlogCommentTest() throws IllegalAccessException, InvocationTargetException {
        BlogPost post = generateBlogPost();
        blogStore.storeBlogPost(post);
        BlogComment comment = generateBlogComment(post.getId());
        blogStore.storeBlogComment(comment);
        String newText = UUID.randomUUID().toString();    
        
        comment.setCommentText(newText);
        blogStore.updateBlogComment(comment.getId(), comment);
        BlogComment updatedComment = blogStore.getBlogCommentById(comment.getId());
        Assert.assertEquals(newText, updatedComment.getCommentText());
    }

	@Test
    public void getAllCommentsTest(){
        int expectedSize = 100;

        IntStream.range(0, expectedSize).parallel().forEach(item -> {
            BlogPost post = generateBlogPost();
            blogStore.storeBlogPost(post);
            BlogComment comment = generateBlogComment(post.getId());
            blogStore.storeBlogComment(comment);
        });

        List<BlogComment> allBlogComments = blogStore.getAllComments();

        Assert.assertEquals(expectedSize, allBlogComments.size());

    }
	@Test
    public void getAllCommentsByPostIdTest(){
        int expectedSize = 100;
        BlogPost post = generateBlogPost();
        blogStore.storeBlogPost(post);
        IntStream.range(0, expectedSize).parallel().forEach(item -> {
            BlogComment comment = generateBlogComment(post.getId());
            blogStore.storeBlogComment(comment);
        });

        List<BlogComment> allBlogCommentsByPostId = blogStore.getAllCommentsByPostId(post.getId());

        Assert.assertEquals(expectedSize, allBlogCommentsByPostId.size());

    }
}
