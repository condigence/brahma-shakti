package com.condigence.service.impl;

import com.condigence.dto.CartDTO;
import com.condigence.dto.ProductDTO;
import com.condigence.dto.SubscriptionDetailDTO;
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
	public void subscribe(SubscriptionDetailDTO subscriptionDTO) {
		Subscription subscription = new Subscription();
		subscription.setFrequency(subscriptionDTO.getFrequency());
		subscription.setFromDate(subscriptionDTO.getFromDate());
		subscription.setId(subscriptionDTO.getId());
		subscription.setNoOfDays(subscriptionDTO.getNoOfDays());
		subscription.setToDate(subscriptionDTO.getToDate());
		subscription.setProductId(subscriptionDTO.getProductDTO().getId());
		subscriptionRepository.save(subscription);
	}

	@Override
	public void unSubscribe(SubscriptionDetailDTO subscriptionDTO) {
		Subscription subscription = subscriptionRepository.findById(subscriptionDTO.getId()).get();
		subscriptionRepository.delete(subscription);
	}

	@Override
	public CartDTO getMySubscriptionsByUserId(String userId) {

		CartDTO cartDTO = new CartDTO();
		List<SubscriptionDetailDTO> subscriptionsList = new ArrayList<>();
		List<Subscription> subscriptions = repository.findByUserId(userId);
		for(Subscription subscription : subscriptions){
			SubscriptionDetailDTO dto = new SubscriptionDetailDTO();

			Product product = productRepository.findOneById(subscription.getProductId());

			ProductDTO  productDTO =  new ProductDTO();
			productDTO.setId(product.getId());
			productDTO.setCategory(product.getCategory());
			productDTO.setDescription(product.getDescription());
			productDTO.setPrice(product.getPrice());
			dto.setProductDTO(productDTO);

			User user = userRepository.findByContact(subscription.getUserId());

			UserDTO userDTO = new UserDTO();
			userDTO.setRegistered(userDTO.isRegistered());
			userDTO.setContact(userDTO.getContact());

			dto.setFrequency(subscription.getFrequency());
			dto.setId(subscription.getId());
			dto.setFromDate(subscription.getFromDate());
			dto.setNoOfDays(subscription.getNoOfDays());
			dto.setToDate(subscription.getToDate());
			subscriptionsList.add(dto);
		}

		cartDTO.setSubscriptionDetails(subscriptionsList);
		return cartDTO;
	}
}
