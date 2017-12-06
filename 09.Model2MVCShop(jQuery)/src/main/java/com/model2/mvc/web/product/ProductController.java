package com.model2.mvc.web.product;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@Controller
@RequestMapping("/product/*")
public class ProductController {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	public ProductController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	@RequestMapping(value="addProduct", method=RequestMethod.GET)
	public String addProduct() throws Exception{
		
		System.out.println("/addProductView.do");
				
		return "forward:/product/addProductView.jsp";	
	}
	
	@RequestMapping(value="addProduct", method=RequestMethod.POST)
	public String addProduct( @ModelAttribute("product")Product product ) throws Exception{
		
		System.out.println("/addProdut.do");

		String manuDate;
		manuDate = product.getManuDate().replaceAll("-", "");
		product.setManuDate(manuDate);
		productService.addProduct(product);
		
		return "forward:/product/addProduct.jsp";
	}
	
	@RequestMapping(value="getProduct")
	public String getProduct( @RequestParam("prodNo") int prodNo, Map map,
							@RequestParam("menu") String menu, HttpServletResponse response, HttpServletRequest request) throws Exception{
		
		System.out.println("/getProduct.do");
		
		Product product = productService.findProduct(prodNo);
				
		map.put("product", product);
		
		String history = null;
		Cookie[] cookies = request.getCookies();
		if (cookies!=null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals("history")) {
					history = cookie.getValue();
				}
			}
		}
		history += ","+request.getParameter("prodNo");
		Cookie cookie = new Cookie("history",history);	

		response.addCookie(cookie);
		
		System.out.println("메뉴테스트"+menu);
		
		if(menu.equals("manage")) {
			return "forward:/product/updateProductView.jsp";
		}
		return "forward:/product/getProduct.jsp";
	}
	
	@RequestMapping(value="updateProductView", method=RequestMethod.POST)
	public String updateProduct(@RequestParam("prodNo") int prodNo, Map map ) throws Exception{
		
		System.out.println("/updateProductView.do");
		
		Product product = productService.findProduct(prodNo);
		
		map.put("product", product);
		
		System.out.println("너는 누구냐");
		
		return "forward:/product/updateProduct.jsp";
	}
	
	@RequestMapping(value="updateProduct", method=RequestMethod.POST)
	public String updateProduct(@ModelAttribute("product") Product product, Map map) throws Exception{
		
		System.out.println("/updateProduct.do");
		
		productService.updateProduct(product);
		
		System.out.println("너구나!!!!!!!!!");

		
		return "forward:/product/updateProduct.jsp";
	}
	
	@RequestMapping(value="listProduct")
	public String listProduct(@ModelAttribute("search") Search search,
								Model model, HttpServletRequest request ) throws Exception {
		
		System.out.println("/listProduct.do");
		
		String sort = search.getSort();
		String order = search.getOrder();
		System.out.println(order);
		//search.setOrder(order);
//		if(sort != null) {
//			search.setSort(sort);
//		}
		//search.setOrder(order);
	
		System.out.println("소팅확인 : "+sort);
		System.out.println("오더확인 : "+order);
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String, Object> map = productService.getProductList(search);
//		List list =(List)map.get("list");
//		System.out.println(list.get(0));
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		model.addAttribute("list",map.get("list"));
		model.addAttribute("resultPage",resultPage);
		model.addAttribute("search",search);
		model.addAttribute("menu",request.getParameter("menu"));
		model.addAttribute("listPurchase",map.get("listPurchase"));
		
		System.out.println("search 테스트"+search);
		
		return "forward:/product/listProduct.jsp";
	}
	
}
