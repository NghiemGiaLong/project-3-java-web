package com.gialong.blog.service.impl;

import com.gialong.blog.entity.Category;
import com.gialong.blog.entity.Post;
import com.gialong.blog.entity.User;
import com.gialong.blog.entity.ReactionType; // <--- QUAN TRỌNG: Import Enum
import com.gialong.blog.exception.ResourceNotFoundException;
import com.gialong.blog.payload.PostDto;
import com.gialong.blog.payload.PostResponse;
import com.gialong.blog.repository.CategoryRepository;
import com.gialong.blog.repository.PostRepository;
import com.gialong.blog.repository.UserRepository;
import com.gialong.blog.repository.CommentRepository;
import com.gialong.blog.repository.ReactionRepository;
import com.gialong.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReactionRepository reactionRepository;

    public PostServiceImpl(PostRepository postRepository,
                           CategoryRepository categoryRepository,
                           UserRepository userRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        Post post = mapToEntity(postDto);
        post.setCategory(category);
        post.setUser(user);
        post.setPublished(true);

        Post newPost = postRepository.save(post);
        return mapToDTO(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        return getPostResponse(posts);
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setImageUrl(postDto.getImageUrl());
        post.setCategory(category);

        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    @Override
    public PostResponse getPostsByCategoryId(Long categoryId, int pageNo, int pageSize, String sortBy, String sortDir) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        List<Post> posts = postRepository.findByCategoryId(categoryId);

        int start = Math.min(pageNo * pageSize, posts.size());
        int end = Math.min((start + pageSize), posts.size());
        List<PostDto> content = posts.subList(start, end).stream().map(this::mapToDTO).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(pageNo);
        postResponse.setPageSize(pageSize);
        postResponse.setTotalElements(posts.size());
        postResponse.setTotalPages((int) Math.ceil((double) posts.size() / pageSize));
        postResponse.setLast(end == posts.size());
        return postResponse;
    }

    @Override
    public PostResponse searchPosts(String query, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findByTitleContaining(query, pageable);
        return getPostResponse(posts);
    }

    private PostResponse getPostResponse(Page<Post> posts) {
        List<PostDto> content = posts.getContent().stream().map(this::mapToDTO).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    private PostDto mapToDTO(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        postDto.setImageUrl(post.getImageUrl());
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setUpdatedAt(post.getUpdatedAt());
        postDto.setPublished(post.isPublished());

        if (post.getCategory() != null) {
            postDto.setCategoryId(post.getCategory().getId());
            postDto.setCategoryName(post.getCategory().getCategoryName());
        }

        if (post.getUser() != null) {
            postDto.setUserId(post.getUser().getId());
            postDto.setAuthorName(post.getUser().getFullName());
        }

        if(commentRepository != null) {
            postDto.setCommentCount(commentRepository.countByPostId(post.getId()));
        }

        // --- SỬA ĐOẠN NÀY ---
        if(reactionRepository != null) {
            // Dùng Enum ReactionType thay vì String "LIKE"/"DISLIKE"
            postDto.setLikeCount(reactionRepository.countByPostIdAndType(post.getId(), ReactionType.LIKE));
            postDto.setDislikeCount(reactionRepository.countByPostIdAndType(post.getId(), ReactionType.DISLIKE));
        }

        return postDto;
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setImageUrl(postDto.getImageUrl());
        return post;
    }
}