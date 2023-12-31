package com.lhs.sts.ex1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/test")
public class TestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	/*
	 * 1. "/hello" 라는 URI로 요청이 들어오면,
	 * 2. "Hello REST!!" 라는 문자열을 StringHttpMessageConverter를 이용해 response message의 body에 담아 브라우저로 반환한다.
	 */
	@RequestMapping("/hello")
	public String hello() {
		return "Hello REST!!";
	}
	
	/*
	 * 1. "/member" 라는 URI로 요청이 들어오면,
	 * 2. memberVO 객체를 MappingJackson2HttpMessageConverter로 json 타입으로 변환, response message의 body에 담아 브라우저로 반환한다.
	 */
	@RequestMapping("/member")
	public MemberVO member() {
		MemberVO memberVO = new MemberVO();
		memberVO.setId("hong");
		memberVO.setPwd("1234");
		memberVO.setName("홍길동");
		memberVO.setEmail("hong@test.com");
		return memberVO;
	}
	
	/*
	 * 1. "/members" 라는 URI로 요청이 들어오면,
	 * 2. List<MemberVO> 객체를 MappingJackson2HttpMessageConverter로 json 타입으로 변환, response message의 body에 담아  브라우저로 반환한다.
	 */
	@RequestMapping("/members")
	public List<MemberVO> members() {
		List<MemberVO> members = new ArrayList<MemberVO>();
		
		for (int i = 0; i < 10; i++) {
			MemberVO memberVO = new MemberVO();
			memberVO.setId("hong" + i);
			memberVO.setPwd("1234" + i);
			memberVO.setName("홍길동" + i);
			memberVO.setEmail("hong" + i + "@test.com");
			members.add(memberVO);	
		}
		return members;
	}
	
	/*
	 * 1. "/membersMap" 이라는 URI로 요청이 들어오면,
	 * 2. Map<Integer, MemberVO> 객체를 MappingJackson2HttpMessageConverter로 json 타입으로 변환, response message의 body에 담아 브라우저로 반환한다.
	 */
	@RequestMapping("/membersMap")
	public Map<Integer, MemberVO> membersMap() {
		Map<Integer, MemberVO> membersMap = new HashMap<Integer, MemberVO>();
		for (int i = 0; i < 10; i++) {
			MemberVO memberVO = new MemberVO();
			memberVO.setId("hong" + i);
			memberVO.setPwd("1234" + i);
			memberVO.setName("홍길동" + i);
			memberVO.setEmail("hong" + i + "@test.com");
			membersMap.put(i, memberVO);
		}	
		return membersMap;
	}
	
	/*
	 * 1. "/notice/{num}" 이라는 URI로 요청이 들어오면,
	 * 2. @PathVariable을 사용해 경로에 포함 된 데이터를 추출한다.
	 * 3. 추출한 int값을 MappingJackson2HttpMessageConverter로 json 타입으로 변환, response message의 body에 담아 브라우저로 반환한다.
	 */
	@RequestMapping(value = "/notice/{num}", method = RequestMethod.GET)
	public int notice(@PathVariable int num) throws Exception {
		return num;
	}
	
	/*
	 * 1. "/notice/{str}" 이라는 URI로 요청이 들어오면,
	 * 2. @PathVariable을 사용해 경로에 포함 된 데이터를 추출한다.
	 * 3. 추출한 String 값을 StringHttpMessageConverter를 이용해 response message의 body에 담아 브라우저로 반환한다.
	 */
	@RequestMapping(value = "/notice2/{str}", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
	public String notice2(@PathVariable String str) throws Exception {
		return str;
	}
	
	/*
	 * 1. "/info" 라는 URI로 요청이 들어오면,
	 * 2. @RequestBody를 사용해 json 형태로 전송 된 데이터를 Java의 Object 형태로 변환한다.
	 * 3. 변환한 데이터를 log를 통해 console에 출력한다.
	 */
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public void modify(@RequestBody MemberVO memberVO) {
		logger.info("memberVO: {}", memberVO);
	}	
	
	/*
	 * 1. "/members2" 라는 URI로 요청이 들어오면,
	 * 2. List<MemberVO> 형태의 객체를 만들고,
	 * 3. HttpEntity를 상속받은 ResponseEntity 객체를 통해, 
	 * 4. header에 http 상태를 입력하고, body에 만든 객체를 담아 브라우저로 반환한다.
	 * 5. 이때 body 부분에 객체가 들어가 있기 때문에 MappingJackson2HttpMessageConverter가 동작한다.
	 */
	@RequestMapping("/members2")
	public ResponseEntity<?> members2() {
		List<MemberVO> members = new ArrayList<MemberVO>();
		for (int i = 0; i < 10; i++) {
			MemberVO memberVO = new MemberVO();
			memberVO.setId("hong" + i);
			memberVO.setPwd("1234" + i);
			memberVO.setName("홍길동" + i);
			memberVO.setEmail("hong" + i + "@test.com");
			members.add(memberVO);
		}
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(members);
	}
	
	/*
	 * 1. "/res3" 라는 URI로 요청이 들어오면,
	 * 2. Http Response Message의 header 부분에 값을 입력하기 위해 HttpHeaders 객체를 생성한다.
	 * 3. 브라우저로 반환할 데이터를 작성한다.
	 * 4. ResponseEntity 객체에 (반환할 데이터, header에 입력할 데이터, http 상태코드) 를 작성해 return한다.
	 * 5. 이때 body 부분에 string 데이터가 들어가 있기 때문에 StringHttpMessageConverter가 동작한다. 
	 */
	@RequestMapping("/res3")
	public <T> ResponseEntity<T> res3() {
		HttpHeaders responseHeader = new HttpHeaders();
		responseHeader.add("Content-Type", "text/html; charset=utf-8");
		
		StringBuilder message = new StringBuilder();
		message.append("<script>");
		message.append(" alert('새 회원을 등록합니다.');");
		message.append(" location.href='/sts/test/members2'");
		message.append("</script>");
		return new ResponseEntity(message, responseHeader, HttpStatus.CREATED);	
	}

}
