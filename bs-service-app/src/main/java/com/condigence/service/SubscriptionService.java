package com.condigence.service;


import com.condigence.bean.OrderBean;
import com.condigence.dto.SubscriptionDTO;

import java.util.List;

public interface SubscriptionService {

	void subscribe(SubscriptionDTO subscriptionDTO);

	void unSubscribe(SubscriptionDTO subscriptionDTO);

	List<SubscriptionDTO> getMySubscriptionsByUserId(String userId);


}
