package ru.meinone.tokinoi_gallery_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class TokinoiGalleryAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TokinoiGalleryAppApplication.class, args);
	}
}
