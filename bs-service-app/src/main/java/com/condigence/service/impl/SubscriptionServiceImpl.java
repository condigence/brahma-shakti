package com.condigence.service.impl;

import com.condigence.bean.OrderBean;
import com.condigence.dto.ProductDTO;
import com.condigence.dto.SubscriptionDTO;
import com.condigence.dto.UserDTO;
import com.condigence.model.Product;
import com.condigence.model.Subscription;
import com.condigence.model.User;
import com.condigence.repository.ProductRepository;
import com.condigence.repository.SubscriptionRepository;
import com.condigence.repository.UserRepository;
import com.condigence.service.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	public static final Logger logger = LoggerFactory.getLogger(SubscriptionServiceImpl.class);

	@Autowired
	private SubscriptionRepository repository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;


	@Autowired
	private SubscriptionRepository subscriptionRepository;



	@Override
	public void subscribe(SubscriptionDTO subscriptionDTO) {
		Subscription subscription = new Subscription();
		subscription.setFrequency(subscriptionDTO.getFrequency());
		subscription.setFromDate(subscriptionDTO.getFromDate());
		subscription.setId(subscriptionDTO.getId());
		subscription.setNoOfDays(subscriptionDTO.getNoOfDays());
		subscription.setQuantity(subscriptionDTO.getQuantity());
		subscription.setToDate(subscriptionDTO.getToDate());
		subscription.setProductId(subscriptionDTO.getProductDTO().getId());
		subscription.setUserid(subscriptionDTO.getUserDTO().getId());
		subscriptionRepository.save(subscription);
	}

	@Override
	public void unSubscribe(SubscriptionDTO subscriptionDTO) {
		Subscription subscription = subscriptionRepository.findById(subscriptionDTO.getId()).get();
		subscriptionRepository.delete(subscription);
	}

	@Override
	public List<SubscriptionDTO> getMySubscriptionsByUserId(String userId) {
		List<SubscriptionDTO> subscriptionsList = new ArrayList<>();
		List<Subscription> subscriptions = repository.findByUserId(userId);
		for(Subscription subscription : subscriptions){
			SubscriptionDTO dto = new SubscriptionDTO();

			Product product = productRepository.findOneById(subscription.getProductId());

			ProductDTO  productDTO =  new ProductDTO();
			productDTO.setId(product.getId());
			productDTO.setCategory(product.getCategory());
			productDTO.setDescription(product.getDescription());
			productDTO.setPrice(product.getPrice());
			dto.setProductDTO(productDTO);

			User user = userRepository.findByContact(subscription.getUserid());

			UserDTO userDTO = new UserDTO();
			userDTO.setRegistered(userDTO.isRegistered());
			userDTO.setContact(userDTO.getContact());
			dto.setUserDTO(userDTO);

			dto.setFrequency(subscription.getFrequency());
			dto.setId(subscription.getId());
			dto.setQuantity(subscription.getQuantity());
			dto.setFromDate(subscription.getFromDate());
			dto.setNoOfDays(subscription.getNoOfDays());
			dto.setToDate(subscription.getToDate());
			subscriptionsList.add(dto);
		}


		return subscriptionsList;
	}
}
