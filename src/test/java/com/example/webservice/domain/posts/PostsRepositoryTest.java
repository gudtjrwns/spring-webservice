package com.example.webservice.domain.posts;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void cleanUp() {
        // 이후 테스트 코드에 영향을 끼치지 않기 위해
        // 테스트 코드가 끝날 때 마다 repository를 전체 비우는 작업
        postsRepository.deleteAll();
    }

    @Test
    public void testSavePotstAndGetAll() {
        postsRepository.save(Posts.builder()
                .title("테스트 게시글")
                .content("테스트 본문")
                .author("gudtjrwns@gmail.com")
                .build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);
        Assert.assertThat(posts.getTitle(), is("테스트 게시글"));
        Assert.assertThat(posts.getContent(), is("테스트 본문"));
    }

    @Test
    public void testSaveBaseTimeEntity() {
        LocalDateTime now = LocalDateTime.now();
        postsRepository.save(Posts.builder()
                .title("테스트 게시글")
                .content("테스트 본문")
                .author("테스터")
                .build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);
        Assert.assertTrue(posts.getCreatedDate().isAfter(now));
        Assert.assertTrue(posts.getModifiedDate().isAfter(now));
    }
}
