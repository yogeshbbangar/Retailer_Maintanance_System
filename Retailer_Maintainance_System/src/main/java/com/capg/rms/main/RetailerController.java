package com.capg.rms.main;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.capg.rms.beans.Order;
import com.capg.rms.beans.Product;
import com.capg.rms.beans.Retailer;
import com.capg.rms.services.RetailerMaintainanceSystemServices;

@Controller
@RequestMapping(value = "/retailer")
public class RetailerController {

	@Autowired
	private RetailerMaintainanceSystemServices services;

	AnnotationConfigApplicationContext retailerctx = new AnnotationConfigApplicationContext(Retailer.class);

	AnnotationConfigApplicationContext productctx = new AnnotationConfigApplicationContext(Product.class);

	AnnotationConfigApplicationContext orderctx = new AnnotationConfigApplicationContext(Order.class);

	@RequestMapping(value = "/userHome", method = RequestMethod.GET)
	public String HomePage() {
		return "UserHomePage";
	}

	// user login
	@GetMapping("/login")
	public String loginPage() {
		return "retailerLogin";
	}
	
	@PostMapping("/retailerLogin")
	public ModelAndView login(HttpServletRequest req, ModelAndView mv) {

		int retailerId = Integer.parseInt(req.getParameter("retailer_id"));
		String password = req.getParameter("passwd");

		Retailer retailer = services.loginProfile(retailerId, password);
		System.out.println(retailer);
		
		if (retailer != null) {
			
			HttpSession session = req.getSession();
			session.setAttribute("retailer", retailer);
			mv.setViewName("redirect:./userHome");
			return mv;
		} else {
			mv.setViewName("redirect:./login");
			return mv;
		}
	}

	// logout
	@GetMapping("/logout")
	public void logOut(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.invalidate();
			Cookie cookie[] = req.getCookies();
			if (cookie != null) {
				for (Cookie c : cookie) {
					if (c.getName().equals("JSESSIONID")) {
						c.setMaxAge(0);
						resp.addCookie(c);
						break;
					}
				}
			}
			try {
				resp.sendRedirect("./login");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				resp.sendRedirect("./login");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// user registration

	@GetMapping("/register")
	public String addUserPage() {
		return "RetailerRegister";
	}

	@PostMapping("/retailerRegister")
	public ModelAndView addUser(HttpServletRequest req, ModelAndView mv) {
		Retailer retailer = retailerctx.getBean(Retailer.class, "retailer");

		String regRetailerId = req.getParameter("retailerId");

		retailer.setId((Integer.parseInt(regRetailerId)));
		retailer.setPassword((req.getParameter("password")));
		retailer.setName(req.getParameter("retailerName"));

		String regEmail = req.getParameter("email");
		retailer.setEmail(regEmail);

		System.out.println(retailer);

		boolean state = services.createProfile(retailer);
		String msg = "Failed to Register";
		if (state) {
			msg = "Successful Registered";
		}

		mv.addObject("msg", msg); // req.setAttribute("user",user)
		mv.setViewName("RetailerRegister");
		return mv;
	}

	// user update

	@GetMapping("/update")
	public String updateUserPage() {
		return "RetailerUpdate";
	}

	@PostMapping("/userUpdate")
	public ModelAndView updateUser(HttpServletRequest req, ModelAndView mv) {
		Retailer retailer = retailerctx.getBean(Retailer.class, "retailer");
		Retailer tempUser = (Retailer) req.getSession().getAttribute("retailer");

		int retailerId = tempUser.getId();
		retailer.setId(retailerId);
		String password = req.getParameter("password");

		boolean state = services.updatePassword(retailerId, password);
		String msg = "Failed to Update Profile";
		if (state) {
			msg = "Profile Updated";
			;
		}

		mv.addObject("msg", msg); // req.setAttribute("user",user)
		mv.setViewName("RetailerUpdate");
		return mv;
	}

	@GetMapping("/getProduct")
	public String searchBusPage() {
		return "SearchProduct";
	}

	@PostMapping("/searchProduct")
	public ModelAndView searchProduct(HttpServletRequest req, ModelAndView mv) {

		int productId = 0;
		String ProductId = req.getParameter("productid");
		productId = Integer.parseInt(ProductId);

		Product product = productctx.getBean(Product.class, "product");

		product = services.getProduct(productId);
		String msg = "No Product found";
		if (product != null) {

			msg = product.toString();

		}
		mv.addObject("msg", msg);
		mv.setViewName("SearchProduct");
		return mv;
	}

	@GetMapping("/orderProduct")
	public String addBooking() {
		return "OrderProduct";
	}

	@PostMapping("/searchProducts")
	public ModelAndView searchProducts(HttpServletRequest req, ModelAndView mv) {

		int productId = 0;
		String ProductId = req.getParameter("productid");
		productId = Integer.parseInt(ProductId);

		Product product = productctx.getBean(Product.class, "product");

		product = services.getProduct(productId);
		String msg = "No Product found";
		if (product != null) {

			msg = product.toString();

		}
		mv.addObject("msg", msg);
		mv.setViewName("OrderProduct");
		return mv;
	}

	@PostMapping("/orderProducts")
	public ModelAndView orderProducts(HttpServletRequest req, ModelAndView mv) {
		Order order = orderctx.getBean(Order.class);

		Retailer tempUser = (Retailer) req.getSession().getAttribute("retailer");

		int retailerId = tempUser.getId();
		order.setRetailerId(retailerId);

		String productName = req.getParameter("productname");
		order.setProductName(productName);

		String quantity = req.getParameter("quantity");
		int productQuantity = Integer.parseInt(quantity);
		order.setQuantity(productQuantity);

		String id = req.getParameter("productid");
		int productId = Integer.parseInt(id);
		order.setProductId(productId);

		Product product = services.getProduct(productId);
		double price = product.getPrice();

		String pName = product.getProductName();

		order.setPricePerUnit(price);
		order.setTotalPrice(productQuantity * price);
		if (productName.equals(pName)) {
			Order orders = services.orderProduct(order);
			String ordermsg = "Fail to Order";
			if (orders != null) {
				ordermsg = orders.toString();
			}
			mv.addObject("ordermsg", ordermsg);
			mv.setViewName("OrderProduct");
			return mv;
		} else {
			String msgs = "Product Name not Matched";
			mv.addObject("msgs", msgs);
			mv.setViewName("OrderProduct");
			return mv;
		}
	}

	@GetMapping("/getOrder")
	public String getTicket() {
		return "GetOrder";
	}

	@PostMapping("/getAllOrder")
	public ModelAndView getTicket(HttpServletRequest req, ModelAndView mv) {

		Retailer tempUser = (Retailer) req.getSession().getAttribute("retailer");
		int retailerId = tempUser.getId();

		List<Order> orderList = services.getOrder(retailerId);
		String msg = "No Order";
		if (orderList != null) {
			msg = orderList.toString();
		}
		mv.addObject("msg", msg); // req.setAttribute("user",user)
		mv.setViewName("GetOrder");

		return mv;

	}

}
