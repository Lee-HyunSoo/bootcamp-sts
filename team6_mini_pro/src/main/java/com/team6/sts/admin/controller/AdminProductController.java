package com.team6.sts.admin.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.team6.sts.admin.service.AdminProductService;
import com.team6.sts.vo.ProductVO;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

	@Autowired
	private AdminProductService adminProductService;
	
	@RequestMapping(value = "/adminProductList", method = { RequestMethod.GET, RequestMethod.POST })
	public String adminProductList(@RequestParam(defaultValue = "") String key,
								   @RequestParam(defaultValue = "1") String tpage,
								   HttpServletRequest request,
								   Model model) {
		Map<String, Object> pageMap = adminProductService.adminProductList(key, tpage, request.getContextPath());
		
		model.addAttribute("key", key);
		model.addAttribute("tpage", tpage);
		model.addAttribute("productList", pageMap.get("productList"));
		model.addAttribute("productListSize", pageMap.get("productListSize"));
		model.addAttribute("paging", pageMap.get("paging"));
	
		return "admin/product/productList";
	}
	
	/**
	 * 1. 새로고침 시 접근을 위한 GET
	 * 2. 버튼 누를 시 접근을 위한 POST
	 * @param pseq
	 * @param tpage
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/adminProductDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public String adminProductDetail(@RequestParam String pseq,
								     @RequestParam(defaultValue = "1") String tpage,
								     Model model) {

		String _pseq = pseq.trim();
		
		ProductVO productVO = adminProductService.adminProductDetail(_pseq);
		model.addAttribute("productVO", productVO);

		String kindList[] = { "0", "Heels", "Boots", "Sandals", "Slipers", "Sneakers" };
		model.addAttribute("tpage", tpage);
		
		int index = Integer.parseInt(productVO.getKind().trim());
		model.addAttribute("kind", kindList[index]);

		return "admin/product/productDetail";
	}
	
	/**
	 * 1. 새로고침 시 접근을 위한 GET
	 * 2. 버튼 누를 시 접근을 위한 POST
	 * @param pseq
	 * @param tpage
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/adminProductUpdateForm", method =  { RequestMethod.GET, RequestMethod.POST })
	public String adminProductUpdateForm(@RequestParam String pseq,
										@RequestParam(defaultValue = "1") String tpage,
										Model model) {
		String _pseq = pseq.trim();

		String kindList[] = { "Heels", "Boots", "Sandals", "Slipers", "Sneakers" };
		model.addAttribute("productVO", adminProductService.adminProductUpdateForm(_pseq));
		model.addAttribute("tpage", tpage);
		model.addAttribute("kindList", kindList);

		return "admin/product/productUpdate";
	}
	
	@RequestMapping(value = "/adminProductUpdate", method = RequestMethod.POST)
	public String adminProductUpdate(HttpServletRequest request) throws IOException {
		HttpSession session = request.getSession();

		int sizeLimit = 5 * 1024 * 1024;
		String savePath = "resources/product_images";
		ServletContext context = session.getServletContext();
		String uploadFilePath = context.getRealPath(savePath);

		MultipartRequest multi = new MultipartRequest(request, // 1. 요청 객체
				uploadFilePath, // 2. 업로드될 파일이 저장될 파일 경로명
				sizeLimit, // 3. 업로드될 파일의 최대 크기(5Mb)
				"UTF-8", // 4. 인코딩 타입 지정
				new DefaultFileRenamePolicy() // 5. 덮어쓰기를 방지 위한 부분
		); // 이 시점을 기해 파일은 이미 저장이 되었다

		adminProductService.adminProductUpdate(multi);
		return "redirect:/admin/products/adminProductList";
	}
	
	/**
	 * 1. 새로고침 시 접근을 위한 GET
	 * 2. 버튼 누를 시 접근을 위한 POST
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/adminProductWriteForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String adminProductWriteForm(Model model) {
		String kindList[] = { "Heels", "Boots", "Sandals", "Slipers", "Sneakers" };
		model.addAttribute("kindList", kindList);
		return "admin/product/productWrite";
	}
	
	@RequestMapping(value = "adminProductWrite", method = RequestMethod.POST)
	public String adminProductWrite(HttpServletRequest request) throws IOException {
		HttpSession session = request.getSession();
		
		int sizeLimit = 5 * 1024 * 1024;
		String savePath = "resources/product_images";
		ServletContext context = session.getServletContext();
		String uploadFilePath = context.getRealPath(savePath);
		
		MultipartRequest multi = new MultipartRequest(request, // 1. 요청 객체
				uploadFilePath, // 2. 업로드될 파일이 저장될 파일 경로명
				sizeLimit, // 3. 업로드될 파일의 최대 크기(5Mb)
				"UTF-8", // 4. 인코딩 타입 지정
				new DefaultFileRenamePolicy() // 5. 덮어쓰기를 방지 위한 부분
		); // 이 시점을 기해 파일은 이미 저장이 되었다
		
		adminProductService.adminProductWrite(multi);
		return "redirect:/admin/products/adminProductList";
	}
	
	@RequestMapping(value = "/adminProductDelete", method = RequestMethod.POST)
	public String adminProductDelete(@RequestParam String pseq) {
		System.out.println("pseq" + pseq);
		adminProductService.adminProductDelete(Integer.parseInt(pseq));
		return "redirect:/admin/products/adminProductList";
	}

}
