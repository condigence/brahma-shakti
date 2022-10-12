package com.condigence.service;


import com.condigence.dto.SubscriptionDetailDTO;

import java.util.List;

public interface SubscriptionService {

	void subscribe(SubscriptionDetailDTO subscriptionDTO);

	void unSubscribe(SubscriptionDetailDTO subscriptionDTO);

	List<SubscriptionDetailDTO> getMySubscriptionsByUserId(String userId);


}
