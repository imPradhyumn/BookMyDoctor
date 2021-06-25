package com.cg.bmd.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.bmd.dto.FeedbackDTO;
import com.cg.bmd.entities.Feedback;
import com.cg.bmd.exception.FeedbackException;
import com.cg.bmd.repository.IFeedbackRepository;
import com.cg.bmd.utils.Util;

@Service
public class FeedbackServiceImp implements IFeedbackService {

	/*
	 *
	 * @Author : Pradhyumn Sharma Description : Service implementation for feedback
	 * added Created : 08-06-2021 Last Modified : 09-06-2021
	 *
	 */

	@Autowired
	IFeedbackRepository feedbackRepo;

	Logger logger = LoggerFactory.getLogger(IFeedbackService.class);

	@Override
	public FeedbackDTO addFeedback(FeedbackDTO feedbackDto) throws FeedbackException {
		Feedback feedback = Util.FeedbackDtoToEntity(feedbackDto);
		return Util.FeedbackEntityToDto(feedbackRepo.save(feedback));
	}

	@Override
	public FeedbackDTO getFeedbackById(int feedbackId) throws FeedbackException {
		if (!feedbackRepo.existsById(feedbackId)) {
			logger.error("No feedback with given id");
			throw new FeedbackException("No feedback with given id");
		}
		return Util.FeedbackEntityToDto(feedbackRepo.getById(feedbackId));
	}

	@Override
	public List<FeedbackDTO> getAllFeedbacksByDoctorId(int doctorId) throws FeedbackException {
		List<Feedback> feedbackList = feedbackRepo.findByDoctorId(doctorId);
		if (feedbackList.isEmpty()) {
			logger.error("No appointments in DB");
			throw new FeedbackException("No appointments in DB");
		}
		return Util.FeedbackEntityToDtoList(feedbackList);
	}

}
