package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {

	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	public PurchaseController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@RequestMapping( value="addPurchase", method=RequestMethod.GET)
	public ModelAndView addPurchase(@ModelAttribute("product") Product product,
										@ModelAttribute("purchase") Purchase purchase,
										@RequestParam("prod_no") int prodNo,
									HttpSession session, Map map) throws Exception{
		
		System.out.println("/addPurchaseView.do");
		
		product = productService.findProduct(prodNo);
		
		User user = (User)session.getAttribute("user");
		
		purchase.setBuyer(user);
		purchase.setPurchaseProd(product);
		
		System.out.println("++++++++++++"+product);
		
		map.put("purchase", purchase);
		
		System.out.println(purchase);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/purchase/addPurchaseView.jsp");

		return modelAndView;
	}
	
	@RequestMapping(value="addPurchase", method=RequestMethod.POST)
	public ModelAndView addPurchase(@ModelAttribute("purchase") Purchase purchase,
									@ModelAttribute("product") Product product,
									@RequestParam("buyerId") String buyerId,
									@RequestParam("prodNo") int prodNo, Map map) throws Exception{
		
		System.out.println("/addPurchase.do");
		System.out.println(prodNo);
		System.out.println(buyerId);

		
		purchase.setPurchaseProd(productService.findProduct(prodNo));
		purchase.setBuyer(userService.getUser(buyerId));
		
		System.out.println("ÆÞÃÄ½º ¸Ê"+purchase);
		
		map.put("purchase", purchase);
		
		purchaseService.addPurchase(purchase);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/purchase/addPurchase.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping(value="getPurchase")
	public ModelAndView getPurchase(@RequestParam("tranNo") int tranNo, Map map) throws Exception{
		
		System.out.println("/getPurchase.do");
		
		Purchase purchase = purchaseService.findPurchase(tranNo);
		
		System.out.println("eofhdf"+purchase);
		
		map.put("purchase", purchase);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/purchase/getPurchase.jsp");
		
		return modelAndView;
	}
	
	/*@RequestMapping("/getPurchase.do")
	public ModelAndView getPurchase2(@RequestParam("prodNo") int prodNo, Map map) throws Exception{
		
		System.out.println("/getPurchase.do");
		
		Purchase purchase = purchaseService.findPurchase(prodNo);
		
		map.put("purchase", purchase);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/purchase/getPurchase.jsp");
		
		return modelAndView;
	}*/
	
	@RequestMapping(value="updatePurchase", method=RequestMethod.GET)
	public ModelAndView updatePurchase(@RequestParam("tranNo") int prodNo, Map map) throws Exception{
		
		System.out.println("/updatePurchaseView.do");
		
		Purchase purchase = purchaseService.findPurchase(prodNo);
		
		map.put("purchase", purchase);
		
		System.out.println(purchase);
				
		//ModelAndView modelAndView = new ModelAndView("/purchase/updatePurchaseView.jsp",map);
		//modelAndView.setViewName();
		
		return new ModelAndView("/purchase/updatePurchaseView.jsp",map);
	}
	
	@RequestMapping(value="updatePurchase", method=RequestMethod.POST)
	public ModelAndView updatePurchase(@ModelAttribute("purchase") Purchase purchase,
										@RequestParam("tranNo") int prodNo,
										HttpSession session ,Map map) throws Exception{
		
		System.out.println("/updatePurchase.do");
		
		purchaseService.updatePurchase(purchase);
		System.out.println("efadfd"+purchase);
		
		User user = (User)session.getAttribute("user");
		purchase = purchaseService.findPurchase(prodNo);
		
		purchase.setBuyer(user);
		
		map.put("purchase", purchase);
		
		System.out.println("¾Æ³ö½à¤®¤·¤·¤¾¤©¤·"+purchase);
		System.out.println("kahfkhawf"+purchase);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/purchase/getPurchase.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping(value="updateTranCode")
	public String updateTranCode(@RequestParam("prodNo")int prodNo, 
										@RequestParam("proTranCode") String proTranCode, 
										@RequestParam("menu") String menu, 
										@ModelAttribute("search") Search search,
										HttpServletRequest request,
										HttpServletResponse response) throws Exception{
		
		System.out.println("/updateTranCode.do");
		
		Product product = productService.findProduct(prodNo);
		
		Purchase purchase = purchaseService.findPurchase2(prodNo);
		System.out.println("12341234"+prodNo);
		System.out.println("11111"+product);
		System.out.println("22222"+purchase);
		if(proTranCode.equals("01")) {
			purchase.setTranCode("02");
			product.setProTranCode("02");
			purchaseService.updateTranCode(purchase);
		}
		
		if(proTranCode.equals("02")) {
			product.setProTranCode("03");
			purchase.setTranCode("03");
			purchaseService.updateTranCode(purchase);
		}
		
		if(proTranCode.equals("02")) {
			return "forward:/purchase/listPurchase";
		}
		return "forward:/purchase/listPurchase";
	}
	
	@RequestMapping(value="listPurchase")
	public String listPurchase(@ModelAttribute("search")Search search, Model model,
								HttpSession session,HttpServletRequest request) throws Exception{
		
		System.out.println("/listPurchase.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		session=request.getSession();
		User user = (User)session.getAttribute("user");
		
		
		
		// Business logic ¼öÇà

		Map<String , Object> map=purchaseService.getPurchaseList(search,user.getUserId());
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		System.out.println("¸Þ´º°ªÅ×½ºÆ®"+request.getParameter("menu"));
		
		// Model °ú View ¿¬°á
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("menu",request.getParameter("menu"));
		model.addAttribute("search", search);
		
		
		return "forward:/purchase/listPurchase.jsp";
	}
	
}
