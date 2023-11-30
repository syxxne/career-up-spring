package com.careerup.careerupspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class CareerUpSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CareerUpSpringApplication.class, args);
	}

	@Bean // WebMvcConfigurer 인터페이스를 구현한 객체를 빈으로 등록
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**") // 특정 엔드포인트에 대한 CORS 설정
						.allowedOrigins("https://4d76-123-111-94-200.ngrok-free.app") // 프론트엔드 로컬 ngrok 서버 주소
						.allowedOrigins("http://localhost:3000") // 프론트엔드 로컬 서버 주소
						.allowedOrigins("https://career-up.live")// 프론트엔드 배포 서버 주소
						.allowedHeaders("*")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH"); // 허용할 HTTP 메서드
			}
		};
	}

}
