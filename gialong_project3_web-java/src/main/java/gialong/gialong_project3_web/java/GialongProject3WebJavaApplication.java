package gialong.gialong_project3_web.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.ComponentScan; // Thêm lại ComponentScan để quét Services/Controllers

@SpringBootApplication
// QUAN TRỌNG: Quét Services, Repositories, Entities (tất cả đều nằm trong com.gialong.blog)
@ComponentScan(basePackages = {"com.gialong.blog", "gialong.gialong_project3_web.java"})
// Chỉ định rõ nơi các Repositories nằm
@EnableJpaRepositories(basePackages = "com.gialong.blog.repository")
// Chỉ định rõ nơi các Entities nằm (đây là nơi Post.java đang ở)
@EntityScan(basePackages = "com.gialong.blog.entity")
public class GialongProject3WebJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GialongProject3WebJavaApplication.class, args);
    }

}