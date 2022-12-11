package com.condigence.service.impl;

import com.condigence.dto.ProductDTO;
import com.condigence.dto.SubscriptionDetailDTO;
import com.condigence.dto.UserDTO;
import com.condigence.model.Subscription;
import com.condigence.repository.SubscriptionRepository;
import com.condigence.service.ProductService;
import com.condigence.service.SubscriptionService;
import com.condigence.service.UserService;
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
	private ProductService productService;

	@Autowired
	private UserService userService;

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
	public List<SubscriptionDetailDTO> getMySubscriptionsByUserId(String userId) {

		List<Subscription> subscriptions = subscriptionRepository.findByUserId(userId);
		List<SubscriptionDetailDTO> subscriptionsList = new ArrayList<>();
		for(Subscription subscription : subscriptions){
			SubscriptionDetailDTO dto = new SubscriptionDetailDTO();
			ProductDTO productDTO =  productService.getProductById(subscription.getProductId());
			dto.setProductDTO(productDTO);
			UserDTO userDTO = userService.getUserById(subscription.getUserId());
			dto.setUserDTO(userDTO);

			dto.setFrequency(subscription.getFrequency());
			dto.setId(subscription.getId());
			dto.setFromDate(subscription.getFromDate());
			dto.setNoOfDays(subscription.getNoOfDays());
			dto.setToDate(subscription.getToDate());
			if(dto.getStatus() == null || dto.getStatus().equalsIgnoreCase("NOT CONFIRMED")){
				dto.setStatus("NOT CONFIRMED");
			}else{
				dto.setStatus("CONFIRMED");
			}
			subscriptionsList.add(dto);
		}

		return subscriptionsList;
	}


}
