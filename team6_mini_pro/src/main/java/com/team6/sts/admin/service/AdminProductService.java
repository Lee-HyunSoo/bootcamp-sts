package com.team6.sts.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oreilly.servlet.MultipartRequest;
import com.team6.sts.dao.ProductDAO;
import com.team6.sts.vo.ProductVO;

@Service
public class AdminProductService {

	@Autowired
	private ProductDAO productDAO;

	public Map<String, Object> adminProductList(String key, String tpage, String path) {
		List<ProductVO> productList = productDAO.listProduct(Integer.parseInt(tpage), key);
		String paging = productDAO.pageNumber(Integer.parseInt(tpage), key, path);
		
		Map<String, Object> pageMap = new HashMap<String, Object>();
		pageMap.put("productList", productList);
		pageMap.put("productListSize", productList.size());
		pageMap.put("paging", paging);
		
		return pageMap;
	}

	public ProductVO adminProductDetail(String pseq) {
		return productDAO.getProduct(pseq);
	}

	public ProductVO adminProductUpdateForm(String pseq) {
		return productDAO.getProduct(pseq);
	}

	public void adminProductUpdate(MultipartRequest multi) {
		ProductVO productVO = new ProductVO();
		productVO.setPseq(Integer.parseInt(multi.getParameter("pseq")));
		productVO.setKind(multi.getParameter("kind"));
		productVO.setName(multi.getParameter("name"));
		productVO.setPrice1(Integer.parseInt(multi.getParameter("price1")));
		productVO.setPrice2(Integer.parseInt(multi.getParameter("price2")));
		productVO.setPrice3(Integer.parseInt(multi.getParameter("price2")) - Integer.parseInt(multi.getParameter("price1")));
		productVO.setContent(multi.getParameter("content"));
		
		if (multi.getFilesystemName("image") == null)
			productVO.setImage(multi.getParameter("nonmakeImg"));
		else 
			productVO.setImage(multi.getFilesystemName("image"));
	
		if ((multi.getParameter("bestyn") == null))
			productVO.setBestyn("n");
		else
			productVO.setBestyn(multi.getParameter("bestyn"));			
	
		if ((multi.getParameter("useyn") == null))
			productVO.setUseyn("n");
		else
			productVO.setUseyn(multi.getParameter("useyn"));			
		
		productDAO.updateProduct(productVO);
	}

	public void adminProductWrite(MultipartRequest multi) {
		ProductVO productVO = new ProductVO();
		productVO.setKind(multi.getParameter("kind"));
		productVO.setName(multi.getParameter("name"));
		productVO.setPrice1(Integer.parseInt(multi.getParameter("price1")));
		productVO.setPrice2(Integer.parseInt(multi.getParameter("price2")));
		productVO.setPrice3(
				Integer.parseInt(multi.getParameter("price2")) - Integer.parseInt(multi.getParameter("price1")));
		productVO.setContent(multi.getParameter("content"));
		productVO.setImage(multi.getFilesystemName("image"));
		
		if ((multi.getParameter("bestyn") == null))
			productVO.setBestyn("n");
		else
			productVO.setBestyn(multi.getParameter("bestyn"));			
	
		if ((multi.getParameter("useyn") == null))
			productVO.setUseyn("n");
		else
			productVO.setUseyn(multi.getParameter("useyn"));			

		productDAO.insertProduct(productVO);	
	}

	
	public void adminProductDelete(int pseq) {
		productDAO.deleteProduct(pseq);
	}
	
	
}
