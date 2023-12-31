package com.lhs.sts.bbs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lhs.sts.ex1.TestController;

@RestController
@RequestMapping("/boards")
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	/*
	 * 1. "/all" 이라는 URI로 요청이 들어오면,
	 * 2. List<ArticleVO> 형태의 객체를 만들고,
	 * 3. ResponseEntity 객체에 HttpStatus 설정, message body에 들어갈 객체를 담아 브라우저로 반환한다.
	 * 4. 이때 body 부분에 객체가 들어가 있기 때문에 MappingJackson2HttpMessageConverter가 동작한다.
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<?> listArticles() {
		logger.info("listArticles 메서드 호출");
		
		List<ArticleVO> articles = new ArrayList<ArticleVO>();
		for (int i = 0; i < 10; i++) {
			ArticleVO articleVO = new ArticleVO();
			articleVO.setArticleNo(i);
			articleVO.setWriter("홍길동" + i);
			articleVO.setTitle("안녕하세요" + i);
			articleVO.setContent("새 상품을 소개합니다." + i);
			articles.add(articleVO);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(articles);
	}
	
	/*
	 * 1. "/{articleNo}" 이라는 URI로 요청이 들어오면,
	 * 2. @PathVariable로 경로에 포함 된 데이터를 잡아내고, ArticleVO 객체를 만든다.
	 * 3. ResponseEntity 객체에 HttpStatus 설정, message body에 들어갈 객체를 담아 브라우저로 반환한다.
	 * 4. 이때 body 부분에 객체가 들어가 있기 때문에 MappingJackson2HttpMessageConverter가 동작한다.
	 * 5. int는 primitive type 이기 때문에 빈 데이터가 들어오면 null 값이 들어오게 된다.
	 * 6. 이러면 NullPointerException이 터질 수 있기 때문에, 이를 방지하기 위해 reference type인 Integer를 사용한다.
	 */
	@RequestMapping(value = "/{articleNo}", method = RequestMethod.GET)
	public ResponseEntity<?> findArticles(@PathVariable Integer articleNo) {
		logger.info("findArticles 메서드 호출");
		
		ArticleVO articleVO = new ArticleVO();
		articleVO.setArticleNo(articleNo);
		articleVO.setWriter("홍길동");
		articleVO.setTitle("안녕하세요");
		articleVO.setContent("홍길동 글 입니다.");
		return ResponseEntity.status(HttpStatus.OK).body(articleVO);
	}
	
	/*
	 * 1. "" 이라는 URI로 요청이 들어오면, 즉 jsp에서 AJAX를 통해 요청이 들어온다면,
	 * 2. @RequestBody로 AJAX 통해 전달 된 json 형태의 데이터를 Java의 Object로 변환한다.
	 * 3. ResponseEntity 객체에 HttpStatus 설정, message body에 들어갈 String을 담아 브라우저로 반환한다.
	 * 4. AJAX 성공 여부에 따라 success / error 중 하나가 동작한다.
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> addArticle(@RequestBody ArticleVO articleVO) {
		try {
			logger.info("addArticle 메서드 호출");
			logger.info("articleVO: {}", articleVO);
			return ResponseEntity.status(HttpStatus.OK).body("ADD_SUCCEEDED");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	/*
	 * 1. "/{articleNo}" 이라는 URI로 요청이 들어오면, 즉 jsp에서 AJAX를 통해 요청이 들어온다면,
	 * 2. @RequestBody로 AJAX 통해 전달 된 json 형태의 데이터를 Java의 Object로 변환한다.
	 * 3. ResponseEntity 객체에 HttpStatus 설정, message body에 들어갈 String을 담아 브라우저로 반환한다.
	 * 4. AJAX 성공 여부에 따라 success / error 중 하나가 동작한다.
	 */
	@RequestMapping(value = "/{articleNo}", method = RequestMethod.PUT)
	public ResponseEntity<?> modArticle(@RequestBody ArticleVO articleVO) {
		try {
			logger.info("modArticle 메서드 호출");
			logger.info("articleNo: {}", articleVO.getArticleNo());
			logger.info("articleVO: {}", articleVO);
			return ResponseEntity.status(HttpStatus.OK).body("MOD_SUCCEEDED");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	/*
	 * 1. "/{articleNo}" 이라는 URI로 요청이 들어오면, 즉 jsp에서 AJAX를 통해 요청이 들어온다면,
	 * 2. @PathVariable로 경로에 포함 된 데이터를 잡아낸다.
	 * 3. ResponseEntity 객체에 HttpStatus 설정, message body에 들어갈 String을 담아 브라우저로 반환한다.
	 * 4. AJAX 성공 여부에 따라 success / error 중 하나가 동작한다.
	 */
	@RequestMapping(value = "/{articleNo}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteArticle(@PathVariable Integer articleNo) {
		try {
			logger.info("deleteArticle 메서드 호출");
			logger.info("articleNo: {}", articleNo);
			return ResponseEntity.status(HttpStatus.OK).body("DELETE_SUCCEEDED");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
}
